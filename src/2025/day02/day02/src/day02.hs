import System.IO
import Data.List.Split
import Data.List (group)

main :: IO ()
main = do
    contents <- readFile "input.txt"
    let list = filter (not . null) $ splitOneOf ",\n" contents
    let tupleList = map parseRange list
    let partOne = sum $ map (\range -> checkRange range 0 digitTwice) tupleList
    --let partTwo = sum $ map (\range -> checkRange range 0 digitRepeated) tupleList
    putStrLn $ "Part 1: " ++ show partOne
    --putStrLn $ "Part 2: " ++ show partTwo

checkRange :: (Int, Int) -> Int -> (Int -> Bool) -> Int
checkRange (start, end) acc digitFunc
    | start > end = acc
    | otherwise = if digitFunc start
                    then checkRange (start+1, end) (acc+start) digitFunc
                    else checkRange (start+1, end) acc digitFunc

parseRange :: String -> (Int, Int)
parseRange str =
    let [startStr, endStr] = splitOn "-" str
        start   = read startStr
        end     = read endStr
    in (start, end)


digitTwice :: Int -> Bool
digitTwice n =
    let s = show n
    in even (length s) && let (a,b) = splitAt (length s `div` 2) s
                          in a == b