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
	sw $t0 varx
	li $t0, 4
	sw $t0 vary
	lw $t0, varx
	move $t1, $t0
	lw $t0, vary
	move $t2, $t0
	add $t0, $t1, $t2
	sw $t0 varcount
	lw $t0, varcount
	move $t1, $t0
	li $t0, 1
	move $t2, $t0
	add $t0, $t1, $t2
	sw $t0 varcount
	lw $t0, varcount
	move $t1, $t0
	li $t0, 102401
	move $t2, $t0
	li $t0, 1
	bgt $t1, $t2, label0
	beq $t1, $t2, label0
	li $t0, 0
label0:
	beq $v0, $0, label1
	li $v0, 1
	li $t0, 999
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
label1:
	li $v0, 10
	syscall
