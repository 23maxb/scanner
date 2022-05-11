	# Program Generated from AST lab by Max Blennemann from output
	.data
varx:
	.word 0
varcount:
	.word 0
vary:
	.word 0
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
	li $v0, 1
	lw $t0, vary
	move $a0, $v0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
