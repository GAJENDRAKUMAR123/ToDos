document.addEventListener("DOMContentLoaded", () => {
    const todoList = document.getElementById("todo-list");
    const todoHeader = document.getElementById("todo-header");
    const form = document.getElementById("todo-form");
    const todoInput = document.getElementById("todo-input");
    const descInput = document.getElementById("desc-input");
    const completedInput = document.getElementById("compltion-input");

    // Load and display all TODOs from backend
    function loadTodos() {
        fetch('/todos') // GET /todos
            .then(response => response.json())
            .then(data => {
                todoList.innerHTML = ''; // clear old list
                 if (data.length === 0) {
                    todoHeader.textContent = "No data : " + data.length;
                }else{
                    todoHeader.textContent = "Available Tasks : " + data.length;
                }
                data.forEach(todo => {
                    const li = document.createElement('li');
                    li.className = "bg-white p-4 rounded shadow-md border border-gray-200";
                li.innerHTML = `
                    <strong>Task: ${todo.todo}</strong><br>
                    Description: ${todo.description}<br>
                    createdAt: ${todo.createdAt}<br>
                    updatedAt: ${todo.updatedAt ? todo.updatedAt : "Not updated yet"}<br><br>
                    <button type="button" onclick="deleteTodo('${todo.id}')" 
                    class="bg-red-500 text-white px-2 py-1 rounded hover:bg-red-600">
                        Delete
                    </button>
                `;
                                
                    todoList.appendChild(li);
                });
            })
            .catch(err => console.error("Failed to load todos", err));
    }

    // Add a new TODO
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const newTodo = {
            todo: todoInput.value,
            description: descInput.value,
            completed: completedInput.value
        };

        fetch('/todos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newTodo)
        })
        .then(res => {
            if (!res.ok) {
                return res.text().then(msg => { throw new Error(msg); });
            }
            todoInput.value = '';
            descInput.value = '';
            completedInput.value = '';
            loadTodos(); // reload list
        })
        .catch(err => alert("Error adding TODO: " + err.message));
    });

    // Delete a TODO by ID
    window.deleteTodo = function(id) {
        fetch(`/todos/${id}`, {
            method: 'DELETE'
        })
        .then(res => {
            if (!res.ok) {
                return res.text().then(msg => { throw new Error(msg); });
            }
            loadTodos(); // reload list
        })
        .catch(err => alert("Error deleting TODO: " + err.message));
    };

    // Initial load
    loadTodos();
});
