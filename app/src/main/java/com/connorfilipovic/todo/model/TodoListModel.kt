package com.connorfilipovic.todo.model

data class TodoListModel(var todoList: List<TodoItemModel> = emptyList()) {
    constructor() : this(emptyList())
}