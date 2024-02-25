package main

import (
	"fmt"
	"math/big"
)

func SLAY(N int) [][]*big.Rat {
	var temp int64
	var matrix [][]*big.Rat = make([][]*big.Rat, N)
	for i := range matrix {
		matrix[i] = make([]*big.Rat, N+1)
		for j := range matrix[i] {
			fmt.Scan(&temp)
			matrix[i][j] = new(big.Rat).SetInt64(temp)
		}
	}
	return matrix
}

func AbsRat(r *big.Rat) *big.Rat {

	if r.Sign() < 0 {
		return new(big.Rat).Neg(r)
	}
	return new(big.Rat).Set(r)
}

func Triangular(matrix [][]*big.Rat, N int) [][]*big.Rat {
	for i := 0; i < N; i++ {
		maxElem := AbsRat(matrix[i][i])
		maxRow := i
		for k := i + 1; k < N; k++ {
			if AbsRat(matrix[k][i]).Cmp(maxElem) > 0 {
				maxElem = AbsRat(matrix[k][i])
				maxRow = k
			}
		}

		matrix[i], matrix[maxRow] = matrix[maxRow], matrix[i]

		if matrix[i][i].Cmp(new(big.Rat).SetInt64(0)) == 0 {
			continue
		}

		for k := i + 1; k < N; k++ {
			coeff := new(big.Rat).Quo(matrix[k][i], matrix[i][i])
			for j := i; j <= N; j++ {
				matrix[k][j] = new(big.Rat).Sub(matrix[k][j], new(big.Rat).Mul(coeff, matrix[i][j]))
			}
		}
	}
	return matrix
}

func Redukt(matrix [][]*big.Rat, N int) [][]*big.Rat {
	for i := 0; i < N; i++ {
		if matrix[i][i].Cmp(new(big.Rat).SetInt64(0)) != 0 {
			div := matrix[i][i]
			for j := 0; j <= N; j++ {
				matrix[i][j] = new(big.Rat).Quo(matrix[i][j], div)
			}
		}
	}
	return matrix
}

func Otv_vect(matrix [][]*big.Rat, N int) ([]*big.Rat, bool) {
	x := make([]*big.Rat, N)
	hasSolution := true

	for i := N - 1; i >= 0; i-- {
		x[i] = new(big.Rat)

		if matrix[i][i].Cmp(new(big.Rat).SetInt64(0)) == 0 {
			if matrix[i][N].Cmp(new(big.Rat).SetInt64(0)) != 0 {
				hasSolution = false
				break
			}
			x[i].SetInt64(0)
			continue
		}

		x[i].Set(matrix[i][N])

		for j := i + 1; j < N; j++ {
			tmp := new(big.Rat).Mul(matrix[i][j], x[j])
			x[i].Sub(x[i], tmp)
		}
		x[i].Quo(x[i], matrix[i][i])
	}

	return x, hasSolution
}

func main() {
	var N int
	fmt.Scan(&N)
	matrix := SLAY(N)
	triangularMatrix := Triangular(matrix, N)
	normalizedMatrix := Redukt(triangularMatrix, N)
	solution, hasSolution := Otv_vect(normalizedMatrix, N)

	if !hasSolution {
		fmt.Println("No solution")
	} else {
		for _, val := range solution {
			fmt.Println(val)
		}
	}
}
