package main

import (
	"fmt"
	"strconv"
)

func add(a []int32, b []int32, p int) []int32 {
	var as, bs string
	for _, digit := range a { 
		as += strconv.FormatInt(int64(digit), p)
	}
	for _, digit := range b { 
		bs += strconv.FormatInt(int64(digit), p)
	}
	asp, _ := strconv.ParseInt(as, p, 64) 
	bsp, _ := strconv.ParseInt(bs, p, 64)
	rez := asp + bsp
	rezs := strconv.FormatInt(rez, p)
	var rezmas []int32
	for i := len(rezs) - 1; i >= 0; i-- { 
		digit, _ := strconv.ParseInt(string(rezs[i]), p, 32)
		rezmas = append(rezmas, int32(digit)) 
	}
	return rezmas
}

func main() {
	fmt.Println(add([]int32{3, 2, 1}, []int32{6, 5, 4}, 10))
	fmt.Println(add([]int32{3, 10, 2}, []int32{15, 5}, 16))
	fmt.Println(add([]int32{1, 0, 1}, []int32{2, 0, 2}, 3))
}
