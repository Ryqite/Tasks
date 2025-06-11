import StringExtension.Companion.eachSymbol
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

class StringExtension {
    companion object {
        fun String.eachSymbol() {
            for (i in this) println(i)
        }
    }
}

class Delay {
    suspend fun doDelay(): String {
        delay(5000)
        return "Imya"
    }
}

class Tasks {
    private val listToDo = mutableListOf<String>()
    fun addTask() {
        println("Введите задачу")
        val note = readln()
        listToDo.add(note)
        println("Задача добавлена в список")
    }

    fun getAllTasks() {
        println("Список всех задач:")
        for (string in listToDo) println("--$string")
        println("Список задач выведен")
    }

    fun deleteTask() {
        println("Введите номер задачи по счету")
        var number = readln().toInt()
        listToDo.removeAt(--number)
        println("Задача удалена")
    }

    fun exit() {
        println("Завершение работы")
        exitProcess(0)
    }
}


class Gui(private val tasks: Tasks) {
    private var choice: Int = 0
    fun menu() {
        while (true) {
            println("Выберите вариант операции:")
            println("1-добавить задачу")
            println("2-список всех задач")
            println("3-удалить задачу")
            println("4-Выход")
            choice = readln().toInt()
            when (choice) {
                1 -> {
                    tasks.addTask()
                }

                2 -> {
                    tasks.getAllTasks()
                }

                3 -> {
                    tasks.deleteTask()
                }

                4 -> {
                    tasks.exit()
                }
            }
        }
    }
}

fun main() {
    val delay = Delay()
    runBlocking {
        val name = async { delay.doDelay() }.await()
        name.eachSymbol()
    }
    val tasks = Tasks()
    val gui = Gui(tasks)
    gui.menu()
}
