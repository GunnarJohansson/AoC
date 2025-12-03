import System.IO
import Control.Monad
import Data.List
import Data.Char

main :: IO ()
main = do
    contents <- readFile "input.txt"
    let listOfStr = lines contents
    print (readCode 50 0 (concatMap splitList listOfStr))

splitList :: String -> [String]
splitList xs = let (letters, digits) = span (not . isDigit) xs
    in [letters, digits]

readCode :: Int -> Int -> [String] -> Int
readCode n acc (x:z:xs) =    
    --Counts if n' becomes zero and increase the accoumulator
    --Want to count including if we pass zero
    let turn = case x of
            "L" -> read z
            "R" -> - read z
        n' = (n + turn) `mod` 100
        acc' = acc + passes n turn 0
    in readCode n' acc' xs
readCode _ acc [] = acc

passes :: Int -> Int -> Int -> Int
passes n turns acc
    | turns == 0 = acc
    | turns > 0 =
        let distToZero = 100 - n
        in if turns >= distToZero
           then passes 0 (turns - distToZero) (acc + 1)
           else acc
    | turns < 0 =
        let distToZero = if n == 0 then 100 else n
        in if abs turns >= distToZero
           then passes 0 (turns + distToZero) (acc + 1)
           else acc