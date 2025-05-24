import kotlin.system.exitProcess
import kotlinx.coroutines.*
fun String.eachSymbol(){
    for (i in this){
        println(i)
    }
}
suspend fun doDelay():String{
    delay(5000)
    return "Imya"
}
fun main() {
    runBlocking {
        val name=async {doDelay()}.await()
        name.eachSymbol()


        val listToDo = mutableListOf<String>()
        var choice: Int = 0
        var task: String
        while (true) {
            println("Выберите вариант операции:")
            println("1-добавить задачу")
            println("2-список всех задач")
            println("3-удалить задачу")
            println("4-Выход")
            choice = readln().toInt()
            when (choice) {
                1 -> {
                    println("Введите задачу")
                    task = readln()
                    listToDo.add(task)
                    delay(5000)
                    println("Задача добавлена в список")
                }

                2 -> {
                    println("Список всех задач:")
                    for (string in listToDo) {
                        println("--$string")
                    }
                    println("Список задач выведен")
                }

                3 -> {
                    println("Введите номер задачи по счету")
                    var number = readln().toInt()
                    listToDo.removeAt(--number)
                }

                4 -> {
                    println("Завершение работы")
                    exitProcess(0)
                }
            }
        }
    }
}