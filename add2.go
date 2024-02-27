package main

import (
	"fmt"
)

func divmod(num int32, den int) (int32, int32) {
	return num / int32(den), num % int32(den)
}

func add(a, b []int32, p int) []int32 {
	reverse(a)
	reverse(b)
	var result []int32
	var temp, ost int32 = 0, 0
	for i, j := len(a)-1, len(b)-1; i >= 0 || j >= 0 || ost > 0; {
		temp = 0
		if i >= 0 {
			temp += a[i]
			i--
		}
		if j >= 0 {
			temp += b[j]
			j--
		}
		temp += ost
		ost, temp = divmod(temp, p)
		result = append(result, temp)
	}

	return result
}

func reverse(s []int32) {
	for i, j := 0, len(s)-1; i < j; i, j = i+1, j-1 {
		s[i], s[j] = s[j], s[i]
	}
}

func main() {

	a := []int32{497243056}
	b := []int32{956067963}

	fmt.Println(add(a, b, 1073741823))
}
