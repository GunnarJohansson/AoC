import Data.Stack
import System.IO
import Data.List

main :: IO ()
main = do
    contents <- readFile "input.txt"
    let lineList = lines contents
    let allDigits = map digits (lines contents)
    
    --Part 1
    let combinationsPartOne = map allCombis allDigits
    let result = map (map sumTwo) combinationsPartOne
    let maxPartOne = map maximum result
    --Part 2
    let largestNumbers = map (maxSubSequence 12) allDigits
    let partTwo = sum $ map digitsToInt largestNumbers

    putStrLn "Part 1: "
    print $ sum maxPartOne
    putStrLn "Part 2: "
    print partTwo

digits:: String -> [Int]
digits = map (read . (:[]))   

sumTwo :: (Int, Int) -> Int
sumTwo (x,y) = x*10 + y

digitsToInt :: [Int] -> Int
digitsToInt = foldl (\n d -> n * 10 + d) 0    

allCombis :: [Int] -> [(Int, Int)]
allCombis [] = []
allCombis (x:xs) = [(x,y) | y <- xs] ++ allCombis xs

combinations :: Int -> [a] -> [[a]]
combinations 0 _ = [[]]
combinations _ [] = []
combinations k (x:xs) = [x:ys | ys <- combinations (k-1) xs] ++ combinations k xs

listToNumerical :: [Int] -> Int
listToNumerical [] = 0
listToNumerical (x:xs) = x * (10 ^ length xs) + listToNumerical xs 

maxSubSequence :: Int -> [Int] -> [Int]
maxSubSequence k xs = go xs [] k
    where
        --No more input, return stack
        go [] stack _ = stack
        go (y:ys) stack remaining
            --If stack is empty, accept y
            | null stack = go ys [y] (remaining - 1)
              --y is greater then the top of the stack and there is enough elements left + current stack
            | y > last stack && length stack + length (y:ys) > k = 
                go (y:ys) (init stack) remaining 
              --Stack is not full
            | length stack < k = go ys (stack ++ [y]) (remaining - 1)
              --Skip y and contiune  
            | otherwise = go ys stack remaining                         
