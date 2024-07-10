import MultiSet
import Data.List (sort, foldl')
import Data.Char (toLower)

-- Function to transform a string into its "ciao" representation
toCiao :: String -> String
toCiao str = sort $ map toLower str

-- Reads a file and constructs an MSet containing words transformed into "ciao"
readMSet :: FilePath -> IO (MSet String)
readMSet filePath = do
  contents <- readFile filePath
  return $ foldl' add empty $ map toCiao $ words contents

-- Writes an MSet to a file in the format "<element> - <multiplicity>"
writeMSet (MS l) filePath = writeFile filePath (unlines $ map toString l)
  where
    toString (x, mx) = show x ++ " - " ++ show mx

main :: IO ()
main = do
  -- Read multisets from specified files
  m1 <- readMSet "aux_files/anagram.txt"
  m2 <- readMSet "aux_files/anagram-s1.txt"
  m3 <- readMSet "aux_files/anagram-s2.txt"
  m4 <- readMSet "aux_files/margana2.txt"

  -- Print a comment on the relationship between m1 and m4
  putStrLn $ if m1 /= m4 && elems m1 == elems m4
             then "m1 and m4 are not equal, but they have the same elements."
             else if m1 == m4
                  then "m1 and m4 are equal."
                  else "m1 and m4 do not have the same elements."

  -- Print a comment on the relationship between m1 and the union of m2 and m3
  putStrLn $ if m1 == union m2 m3
             then "m1 is equal to the union of m2 and m3."
             else "m1 is not equal to the union of m2 and m3."

  -- Write multisets m1 and m4 to specified files
  writeMSet m1 "anag-out.txt"
  writeMSet m4 "gana-out.txt"