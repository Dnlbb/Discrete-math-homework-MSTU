package main

import (
	"fmt"
)

func encode(utf32 []rune) []byte {
	var utf8Bytes []byte
	for _, r := range utf32 {

		switch {
		case r <= 0x7F:
			utf8Bytes = append(utf8Bytes, byte(r))
		case r <= 0x7FF:
			utf8Bytes = append(utf8Bytes, 0xC0|byte(r>>6), 0x80|byte(r&0x3F))
		case r <= 0xFFFF:
			utf8Bytes = append(utf8Bytes, 0xE0|byte(r>>12), 0x80|byte((r>>6)&0x3F), 0x80|byte(r&0x3F))
		default:
			utf8Bytes = append(utf8Bytes, 0xF0|byte(r>>18), 0x80|byte((r>>12)&0x3F), 0x80|byte((r>>6)&0x3F), 0x80|byte(r&0x3F))
		}
	}
	return utf8Bytes
}

func decode(utf8Bytes []byte) []rune {
	var utf32 []rune
	str := string(utf8Bytes)
	for _, r := range str {
		utf32 = append(utf32, r)
	}
	return utf32
}

func main() {
	utf32 := []rune{'β'}
	utf8 := encode(utf32)
	decUtf32 := decode(utf8)
	if fmt.Sprintf("%x", utf32) == fmt.Sprintf("%x", decUtf32) {
		fmt.Println("Good")
	} else {
		fmt.Println("FAIL")
	}
}
