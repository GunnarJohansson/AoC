--- Day 5: Cafeteria ---
import Data.List

main :: IO ()
main = do
    contents <- readFile "input.txt"
    let inputLines = lines contents
    let (ids, ingredient) = splitOnEmpty inputLines
    let rangeIds = getRanges ids
    --Part 1:
    let boolList = map (\x -> map (fresh x) rangeIds) (tail ingredient)
    putStr "Part 1: "
    print $ countTrue boolList

    --Part 2:
    putStr "Part 2 "
    let result = countUnique rangeIds
    print result

{- Part 2 -}
merge :: [(Int, Int)] -> [(Int, Int)]
merge []    = []
merge [x]   = [x]
merge ((a,b):(c,d):rest) 
    | c <= b + 1 = merge ((a, max b d) : rest) --merge overlap
    | otherwise = (a,b) : merge ((c,d): rest) --Keep and contiune

{- Sort based on first tuple, call sorted and calculate sum in merged order. -}
countUnique :: [(Int, Int)] -> Int
countUnique ranges = 
    let sorted = sortOn fst ranges
        merged = merge sorted
        in countRanges merged
{- Part 1:  -}
countRanges :: [(Int, Int)] -> Int
countRanges = sum . map (\(a,b) -> b - a + 1)

countTrue :: [[Bool]] -> Int
countTrue [] = 0
countTrue (x:xs) = if any (==True) x then 1 + countTrue xs else 0 + countTrue xs

fresh :: String -> (Int, Int) -> Bool
fresh s (small, big) = read s >= small && read s <= big

getRanges :: [String] -> [(Int,Int)]
getRanges = map splitOn

splitOn :: String -> (Int, Int)
splitOn s = let (a, _ : b) = break (== '-') s
    in (read a, read b)

split :: Eq a => a -> [a] -> [[a]]
split d [] = []
split d s = x : split d (drop 1 y) where (x,y) = span (/= d) s

splitOnEmpty :: [String] -> ([String], [String])
splitOnEmpty xs = case elemIndex "" xs of
    Just i  -> splitAt i xs
    Nothing -> (xs, [])