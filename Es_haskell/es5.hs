data Expr a = Const a | Sum (Expr a) (Expr a) | Mul (Expr a) (Expr a)

main = do
  eval (Sum (Mul (Const 2) (Const 3)) (Const 4))
