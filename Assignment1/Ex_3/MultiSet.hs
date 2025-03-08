module MultiSet
  ( MSet(..)
  , empty
  , add
  , occs
  , elems
  , subeq
  , union
  , mapMSet
  ) where

-- Define a data type MSet parameterized over type 'a', representing a multi-set with elements of type 'a' and their multiplicities.
data MSet a = MS [(a, Int)]
    deriving (Show)

-- Define equality for MSet instances where elements are considered equal if they have the same elements with the same multiplicities.
instance Eq a => Eq (MSet a) where
  (==) (MS l1) (MS l2) = maybe False null (foldl_maybe remove (Just l2) l1)
    where
      foldl_maybe _ acc [] = acc
      foldl_maybe fn acc (x:xs) = do 
        acc' <- fn acc x
        foldl_maybe fn (Just acc') xs

      remove (Just []) _ = Nothing
      remove (Just ((x, mx) : xs)) (v, mv)
        | x == v && mx == mv = Just xs
        | x == v && mx /= mv = Nothing
        | otherwise = do
            xs' <- remove (Just xs) (v, mv)
            Just ((x, mx) : xs')
      remove _ _ = Nothing

-- Define the Foldable instance for MSet, allowing functions like foldr to operate on MSet.
instance Foldable MSet where
  foldr _ acc (MS []) = acc
  foldr fn acc (MS (x:xs)) = fn (fst x) (foldr fn acc (MS xs))

-- Create an empty multiset.
empty :: MSet a
empty = MS []

-- Add an element to a multiset, incrementing its multiplicity.
add :: Eq a => MSet a -> a -> MSet a
add (MS l) v = MS $ update l
  where
    update [] = [(v, 1)]
    update ((x, mx) : xs)
      | x == v    = (v, mx + 1) : xs
      | otherwise = (x, mx) : update xs

-- Count occurrences of a specific element in the multiset.
occs :: Eq a => MSet a -> a -> Int
occs (MS l) v = maybe 0 id (lookup v l)

-- Retrieve a list of elements in the multiset, without their multiplicities.
elems :: MSet b -> [b]
elems (MS l) = map fst l

-- Check if one multiset is a subset of another.
subeq :: Eq a => MSet a -> MSet a -> Bool
subeq (MS l1) (MS l2) = all check l1
  where
    check (x, mx) = mx <= occs (MS l2) x

-- Union of two multisets, combining elements and their multiplicities.
union :: Eq a => MSet a -> MSet a -> MSet a
union (MS l1) (MS l2) = MS $ foldl insertOrUpdate l1 l2

-- Map a function over the elements of a multiset, transforming them and possibly changing their multiplicities.
mapMSet :: Eq a => (t -> a) -> MSet t -> MSet a
mapMSet fn (MS l) = MS $ foldl insertOrUpdate [] (map (\(x, mx) -> (fn x, mx)) l)

{-
  MSet cannot be a Functor instance with fmap = mapMSet because
  mapMSet does not respect the standard fmap signature for functors.
  The function mapMSet modifies the structure of the MSet by managing
  the multiplicities of elements, and thus violating the expected behavior of fmap.
  In addition, the law of functor composition is not respected
  because of the structural modification of the set.
  
  If we were to define mapMSet as:
    mapMSet fn (MS l) = MS $ map (\(x, mx) -> (fn x, mx)) l, 
  then the function could be considered as an implementation of fmap.
  However, this implementation may return sets that are not well formed,
  such as sets with duplicate elements.
-}

-- Helper function to insert or update an element in a list of key-value pairs.
insertOrUpdate :: (Eq a, Num b) => [(a, b)] -> (a, b) -> [(a, b)]
insertOrUpdate [] (x, mx) = [(x, mx)]
insertOrUpdate ((y, my) : ys) (x, mx)
  | x == y    = (y, my + mx) : ys
  | otherwise = (y, my) : insertOrUpdate ys (x, mx)