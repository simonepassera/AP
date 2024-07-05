package ap.xml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class XMLSerializer {
    public static void serialize(Object[] arr, String fileName) {
        HashMap<String, ClassInfo> analyzedClasses = new HashMap<String, ClassInfo>();
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        for (Object object : arr) {
            if (object != null) {
                Class<?> c = object.getClass();

                analyzedClasses.putIfAbsent(c.getName(), new ClassInfo(c));
                ClassInfo cInfo = analyzedClasses.get(c.getName());

                if (cInfo.isSerializable()) {
                    xml.append("\n<" + cInfo.getName() + ">");

                    for (FieldInfo f : cInfo.getFields()) {
                        String tag = f.getNameAnnotation().isEmpty() ? f.getNameField() : f.getNameAnnotation();

                        xml.append("\n\t<" + tag + " type=\"" + f.getType() + "\">");

                        try {
                            Field field = c.getDeclaredField(f.getNameField());
                            field.setAccessible(true);
                            xml.append(field.get(object));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        xml.append("</" + tag + ">");
                    }

                    xml.append("\n</" + cInfo.getName() + ">");
                } else {
                    xml.append("\n<notXMLable />");
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.append(xml);
        } catch (Exception e) {
            throw new RuntimeException(e);           
        }
    }

    private static class ClassInfo {
        private String name;
        private boolean serializable = false;
        private ArrayList<FieldInfo> fields = new ArrayList<>();;

        public ClassInfo(Class<?> newClass) {
            this.name = newClass.getName();

            if (newClass.isAnnotationPresent(XMLable.class)) {
                serializable = true;

                for (Field f : newClass.getDeclaredFields()) {
                    if (f.isAnnotationPresent(XMLfield.class)) {
                        if (!f.getType().isPrimitive() && !String.class.isAssignableFrom(f.getType()))
                            throw new Error("annotation @XMLfield not applicable to field \"" + f.getName() + "\" of class \"" + this.name + "\"");

                        XMLfield a = f.getAnnotation(XMLfield.class);
                        fields.add(new FieldInfo(f.getName(), a.type(), a.name()));
                    }
                }
            }
        }

        public boolean isSerializable() {
            return serializable;
        }

        public String getName() {
            return name;
        }

        public ArrayList<FieldInfo> getFields() {
            return fields;
        }
    }

    private static class FieldInfo {
        String type;
        String nameField;
        String nameAnnotation;

        public FieldInfo(String nameField, String type, String nameAnnotation) {
            this.type = type;
            this.nameField = nameField;
            this.nameAnnotation = nameAnnotation;
        }

        public String getType() {
            return type;
        }

        public String getNameField() {
            return nameField;
        }

        public String getNameAnnotation() {
            return nameAnnotation;
        }
    }
}

