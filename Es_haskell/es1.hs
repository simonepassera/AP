myreplicateR 0 v = []
myreplicateR n v = v:(myreplicateR (n-1) v)

myreplicateC n v = map (\_ -> v) [1..n]

main = do
  putStrLn "List with myreplicateR 5 'a' :"  
  print (myreplicateR 5 'a')

  putStrLn "List with myreplicateC 6 'h' :"  
  print (myreplicateC 6 'h')  
