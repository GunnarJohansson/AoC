type Matrix  = [String]
--Slow but works, vectors could be quicker
main :: IO ()
main = do
    contents <- readFile "input.txt"
    let matrix = lines contents
    let lessThan4calc = lessThan4 matrix
    print ("Part 1: " ++ show (length lessThan4calc))

    let (finalMatrix, totalRemoved) = clearRolls matrix 0
    print ("Part 2: Total rolls removed " ++ show totalRemoved)

clearRolls :: Matrix -> Int -> (Matrix, Int)
clearRolls matrix acc = 
    let lt4 = lessThan4 matrix
        count = length lt4
    in if count == 0 
        then (matrix, acc)
        else clearRolls (foldl removeAt matrix lt4) (acc + count)

lessThan4 :: Matrix -> [(Int, Int)]
lessThan4 matrix = [ (x,y) | y <- [0..height matrix - 1]
                         , x <- [0..width matrix - 1]
                         , (matrix !! y) !! x == '@'
                         , liveNeighbours matrix (x,y) < 4 ]

countNeighbours :: Matrix -> [[Int]]
countNeighbours matrix =    [[liveNeighbours matrix (x, y) 
                            | x <- [0..width matrix - 1]] 
                            | y <- [0..height matrix - 1]]

removeAt :: Matrix -> (Int, Int) -> Matrix
removeAt matrix (x, y) =    [[if rowIndex == y && colIndex == x then '.' else ch 
                            | (ch, colIndex) <- zip row [0..]] 
                            | (row, rowIndex) <- zip matrix [0..]]

width :: Matrix -> Int
width m = length (head m)

height :: Matrix -> Int
height = length

--Go G.Hutton!
neighboursOffsets :: [(Int, Int)]
neighboursOffsets = [(x,y) | x <- [-1, 0, 1], y <- [-1, 0, 1], (x, y) /= (0,0)]
--Good book!
liveNeighbours :: Matrix -> (Int, Int) -> Int
liveNeighbours matrix (x, y) = length [() | (dx, dy) <- neighboursOffsets, let nx = x + dx, let ny = y + dy, inBounds matrix (nx, ny), (matrix !! ny) !! nx == '@']

inBounds :: Matrix -> (Int, Int) -> Bool
inBounds matrix (x, y) = x >= 0 && x < width matrix && y >= 0 && y < height matrix