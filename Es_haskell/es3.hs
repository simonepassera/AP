myreplicate n v = map (\_ -> v) [1..n]

replR :: (Num t, Enum t) => [a] -> t -> [a]
replR [] n = []
replR (x:xs) n = (myreplicate n x) ++ (replR xs n)

replC :: (Num a1, Enum a1) => [a2] -> a1 -> [a2]
replC l n = concat [myreplicate n x | x <- l]

replC2 xs n = xs >>= \x -> (myreplicate n x)   

main = do
  putStrLn "List = [1,2,3,4,5] (n = 3):"  
  print (replR [1,2,3,4,5] 3)

  putStrLn "List = [1,2,3,4,5] (n = 3):"  
  print (replC [1,2,3,4,5] 3)  

  putStrLn "List = [1,2,3,4,5] (n = 3):"  
  print (replC2 [1,2,3,4,5] 3)
