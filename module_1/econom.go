package main

import (
	"fmt"
)

var cnt int

type StringSet struct {
	elements map[string]bool
}

func NewStringSet() *StringSet {
	return &StringSet{elements: make(map[string]bool)}
}

func (s *StringSet) Add(element string) {
	s.elements[element] = true
	cnt++
}

func (s *StringSet) Contains(element string) bool {
	_, exists := s.elements[element]
	return exists
}

func splExp(s string) (s1 string, s2 string) {
	var balance int = 0
	var ind int
	for i := 0; i < len(s); i++ {
		switch s[i] {
		case '(':
			balance++
		case ')':
			balance--
		}
		if i == 0 {
			continue
		}
		if s[i-1] == ')' && s[i] == '(' && balance == 1 {
			ind = i
			break
		}
	}

	if s[1] != '(' {
		s1 = s[:2]
		s2 = s[2:]
		return
	} else if s[len(s)-1] != ')' {
		s1 = s[:len(s)-1]
		s2 = s[len(s)-1:]
		return
	} else if len(s) == 3 {
		s1 = ""
		s2 = s
		return
	} else {
		s1 = s[:ind]
		s2 = s[ind:]
		return
	}
}

func corrector(s string) (new_s string) {
	_, temp := splExp(s)
	if (s[0] == '#' || s[0] == '$' || s[0] == '@') && len(s) > 2 && temp == s {
		new_s = s[2 : len(s)-1]
		return
	} else if s[0] == '(' && s[len(s)-1] == ')' && len(s) > 1 {
		new_s = s[1 : len(s)-1]
		return
	} else {
		new_s = s
		return
	}
}

func razbor(s string, set *StringSet) {
	if len(s) < 3 {
		return
	}
	corrected := corrector(s)
	if !set.Contains(corrected) && len(s) > 3 {
		set.Add(corrected)
		s1, s2 := splExp(corrected)
		razbor(s1, set)
		razbor(s2, set)
	} else if len(corrector(s)) < 3 {
		return
	} else if set.Contains(corrected) {
		return
	}
}

func main() {
	set := NewStringSet()
	cnt = 0
	var expr1 string
	fmt.Scan(&expr1)
	razbor(expr1, set)
	fmt.Println(cnt)
}
