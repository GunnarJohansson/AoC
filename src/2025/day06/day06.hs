import Data.List
import Data.Char 


type Matrix = [[Int]]

main :: IO ()
main = do
    contents <- readFile "input.txt"
    let list = lines contents                                   --String List
    let strMatrix = map words list                              --[[String]]
    
    let numberMatrix = map (map read) $ init strMatrix          --[[Int]]
    let operators = last strMatrix                              --Find operators in list
    let transposed = transpose numberMatrix                     --transpose matrix
    let op = map parseOp operators                              --turn operators into function
    putStr "Part 1: "
    print $ sum (zipWith foldl1 op transposed)                  --Apply functions op to transposed list, calc sum
    
    {- Part 2, read right to left-}
    let reverseMatrix = map reverse list                        --Reverse matrix
    let clean = cleaned $ transpose (init reverseMatrix)        --clean transposed matrix 
    let reverseOperators = last reverseMatrix                   --Reverse Operators
    let cleanedOps = filter (not . isSpace) reverseOperators    --clean Ops list
    let revOp = map (parseOp . (:[])) cleanedOps                --Create function operators
    let splitedList = splitOnEmpty clean                        --splitList
    let splitedNumbers = map (map read) splitedList             --turn into numbers
    putStr "Part 2: "
    print $ sum (zipWith foldl1 revOp splitedNumbers)           --Apply operators on transposed reversed matrix

parseOp :: String -> (Int -> Int -> Int)
parseOp "*" = (*)
parseOp "+" = (+)
parseOp o   = error ("Unknown operator: " ++ o)

cleaned :: [String] -> [String]
cleaned = map (filter (not . isSpace))

splitOnEmpty :: [String] -> [[String]]
splitOnEmpty [] = []
splitOnEmpty xs =
    let (first, rest) = break (== "") xs
        rest' = dropWhile (== "") rest
    in first : splitOnEmpty rest'