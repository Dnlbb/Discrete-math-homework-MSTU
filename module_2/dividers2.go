package main

import (
	"fmt"
)

func main() {
	var n int
	_, _ = fmt.Scanf("%d", &n)
	arr := MakeMap(n)
	graph := MakeGraph(arr)
	PrintGraph(graph)
}

func MakeMap(n int) map[int][]int {
	arr := make(map[int][]int)
	for i := n; i > 0; i-- {
		if n%i == 0 {
			arr[i] = make([]int, 0)
		}
	}
	for i := range arr {
		for j := 1; j <= n; j++ {
			if i%j == 0 && j != i {
				arr[i] = append(arr[i], j)
			}
		}
	}
	return arr
}

func MakeGraph(arr map[int][]int) map[int][]int {
	graph := make(map[int][]int)

	for u := range arr {
		for _, v := range arr[u] {
			isDirect := true
			for _, w := range arr[u] {
				if w != v && w != u && u%w == 0 && w%v == 0 {
					isDirect = false
					break
				}
			}
			if isDirect {
				graph[u] = append(graph[u], v)
			}
		}
	}
	return graph
}

func PrintGraph(graph map[int][]int) {
	for v, r := range graph {
		for _, i := range r {
			fmt.Printf("%d -> %d\n", v, i)
		}
	}
}
