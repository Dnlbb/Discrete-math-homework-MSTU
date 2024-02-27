package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

var i int

func calculation(s []rune) int64 {
	var rez int64

	if i >= len(s) {
		rez = 0
		return rez
	}

	switch s[i] {
	case '+':
		i++
		rez = calculation(s) + calculation(s)
		return rez
	case '-':
		i++
		rez = calculation(s) - calculation(s)
		return rez
	case '*':
		i++
		rez = calculation(s) * calculation(s)
		return rez
	case '(':
		i++
		rez = calculation(s)
		return rez
	case ')':
		i++
		rez = calculation(s)
		return rez
	case ' ':
		i++
		rez = calculation(s)
		return rez
	default:

		num, err := strconv.ParseInt(string(s[i]), 10, 64)
		if err != nil {
			return 0
		}
		rez = num
		i++
		return rez
	}
}

func main() {
	Scanner := bufio.NewScanner(os.Stdin)
	if Scanner.Scan() {
		s := []rune(Scanner.Text())
		i = 0
		fmt.Print(calculation(s))
	}
}
