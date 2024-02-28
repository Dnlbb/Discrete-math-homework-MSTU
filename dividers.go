package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()
	var x int
	_, err := fmt.Sscanf(scanner.Text(), "%d", &x)
	if err != nil {
		return
	}

	divisors := make(map[int]bool)
	for i := 1; i*i <= x; i++ {
		if x%i == 0 {
			divisors[i] = true
			if i != x/i {
				divisors[x/i] = true
			}
		}
	}

	var sortedDivisors []int
	for d := range divisors {
		sortedDivisors = append(sortedDivisors, d)
	}
	sort.Sort(sort.Reverse(sort.IntSlice(sortedDivisors)))

	fmt.Println("graph {")
	for _, d := range sortedDivisors {
		fmt.Printf("    %d\n", d)
	}
	for _, u := range sortedDivisors {
		for _, v := range sortedDivisors {
			if u > v && u%v == 0 {
				isDirect := true
				for _, w := range sortedDivisors {
					if w > v && w < u && u%w == 0 && w%v == 0 {
						isDirect = false
						break
					}
				}
				if isDirect {
					fmt.Printf("    %d--%d\n", u, v)
				}
			}
		}
	}
	fmt.Println("}")
}
