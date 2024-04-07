package com.example.monstermath.Model

data class MathQuestions(val id: Int, val category: String, val question: String, val correctAnswer: Int, val options: List<Int>)
