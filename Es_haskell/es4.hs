totalLengthR [] = 0
totalLengthR (x:xs) = (if (head x) == 'A' then length x else 0) + totalLengthR xs

main = do
  putStrLn "List = [\"Mela\",\"Arancia\",\"Banana\",\"Apple\"]:"  
  print (totalLengthR ["Mela","Arancia","Banana","Apple"])
