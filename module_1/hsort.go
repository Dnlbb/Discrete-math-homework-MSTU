package main

import "fmt"


func hsort(n int, less func(i, j int) bool, swap func(i, j int)) {
    var heapify func(n, i int)
    heapify = func(n, i int) {
        largest := i
        left := 2*i + 1
        right := 2*i + 2
        if left < n && less(left, largest) {
            largest = left
        }
        if right < n && less(right, largest) {
            largest = right
        }

        if largest != i {
            swap(i, largest)
            heapify(n, largest)
        }
    }
    for i := n/2 - 1; i >= 0; i-- {
        heapify(n, i)
    }
    for i := n - 1; i >= 0; i-- {
        swap(0, i)
        heapify(i, 0)
    }
}

func main() {
    a := []int{10, 3, 76, 34, 23, 32}
    less := func(i, j int) bool {
        return a[i] > a[j]
    }
    swap := func(i, j int) {
        a[i], a[j] = a[j], a[i]
    }
    hsort(len(a), less, swap)
    fmt.Println(a)
}
