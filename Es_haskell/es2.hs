sumOddR [] = 0
sumOddR (x:xs) = x + (sumOddR xs) 

sumOddC l = foldr (+) 0 l

main = do
  putStrLn "Sum odd values of [1,2,3,4,5] :"  
  print (sumOddR [1,2,3,4,5])

  putStrLn "Sum odd values of [1,2,3,4,5] :"  
  print (sumOddC [1,2,3,4,5])  
