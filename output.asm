	# Program Generated from AST lab by Max Blennemann from output
	.text
	.globl main
main:
	li $t0, 2
	sw $v0 varx
	lw $t0, varx
	move $t1, $t0
	li $t0, 1
	move $t2, $t0
	add $t0, $t1, $t2
	sw $v0 vary
	lw $t0, varx
	move $t1, $t0
	lw $t0, vary
	move $t2, $t0
	add $t0, $t1, $t2
	sw $v0 varx
	li $v0, 1
	lw $t0, varx
	move $t1, $t0
	lw $t0, vary
	move $t2, $t0
	mult $t1, $t2
	mflo $t0
	move $a0, $v0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
	lw $t0, varx
	move $t1, $t0
	lw $t0, vary
	move $t2, $t0
	li $t0, 1
	bgt $t1, $t2, a
	li $t0, 0
a:
	li $t0, 0
	sw $v0 varx
	li $v0, 1
	lw $t0, vary
	move $a0, $v0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
