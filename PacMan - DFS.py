import copy

def main():
    # Input Pac-Man's starting position and food's position
    pacman_start_x, pacman_start_y = map(int, input().split())
    food_x, food_y = map(int, input().split())

    # Input grid dimensions
    rows, cols = map(int, input().split())

    # Initialize the game board
    game_board = []
    expanded_nodes = []
    stack = []
    best_path = None

    # Build the game board
    for _ in range(rows):
        game_board.append(list(input()))

    # Define possible movements: up, left, right, down
    movements = [[-1, 0], [0, -1], [0, 1], [1, 0]]

    # Add the starting position to the stack along with an empty path
    stack.append([pacman_start_x, pacman_start_y, []])

    # Explore nodes in the stack
    while stack:
        x, y, path = stack.pop()
        current_path = copy.deepcopy(path)
        current_path.append([x, y])

        expanded_nodes.append([x, y])

        if x == food_x and y == food_y:
            if best_path is None:
                best_path = current_path
                break

        for move in movements:
            next_x, next_y = x + move[0], y + move[1]

            if next_x < 0 or next_x >= rows or next_y < 0 or next_y >= cols:
                continue

            if game_board[next_x][next_y] == "-" or game_board[next_x][next_y] == ".":
                game_board[next_x][next_y] = '='
                stack.append([next_x, next_y, current_path])

    print(len(expanded_nodes))
    for node in expanded_nodes:
        print(f"{node[0]} {node[1]}")

    print(len(best_path) - 1)
    for step in best_path:
        print(f"{step[0]} {step[1]}")

if __name__ == "__main__":
    main()
