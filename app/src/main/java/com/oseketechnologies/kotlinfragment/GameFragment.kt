package com.oseketechnologies.kotlinfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.oseketechnologies.kotlinfragment.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    data class Question(var question : String, var answers : List<String>)

    private var questions : MutableList<Question> = mutableListOf(
        Question(question = "what is android jetpack", answers = listOf("All of the above","Tools","Documentation","libraries")),
        Question(question = "what is the base class for layouts", answers = listOf("ViewGroup", "ViewSet", "ViewCollection","ViewRoot")),
        Question(question = "What layout do you use for complex screen?", answers = listOf("ConstraintLayout","GridLayout","LinearLayout","FrameLayout")),
        Question(question = "What do you use to structure data into the layout?",answers = listOf("Data binding", "Data pushing", "Set text","An onclick method")),
        Question(question = "What method do you use to inflate layout in fragment", answers = listOf("OncreateView()", "onActivityCreated()","onCreateLayout()","onInflateLayout()")),
        Question(question = "Whats the build system for android?", answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
        Question(question = "Which class do you use to create a vector drawable?", answers = listOf("VictorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
        Question(question = "Which of this is an android navigation component", answers = listOf("NavControl", "NavCentral","NavMaster","NavSwitcher")),
        Question(question = "Which Xml element lets you register an activity with the launcher activity?", answers = listOf("intent-filter","app-registry","launcher-registry","app-launcher")),
        Question(question = "What do you use to mark a layout for data binding", answers = listOf("<layout>", "<binding>","<data-binding>", "<dbinding>")))

    lateinit var currentQuestion : Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private var numQuestion = Math.min((questions.size + 1)/2, 3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater, R.layout.fragment_game, container, false)
        binding.game = this

        randomizeQuestion()
        binding.submitButton.setOnClickListener  {
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            var answerIndex = 0
            if(checkedId != -1) {
                questionIndex++
                when (checkedId) {
                    R.id.secondRadioButton -> answerIndex = 1
                    R.id.thirdRadioButton -> answerIndex = 2
                    R.id.fourthRadioButton -> answerIndex = 3
                }

                if(answers[answerIndex] == currentQuestion.answers[0]){
                    if(questionIndex < numQuestion){
                        setQuestion()
                        binding.invalidateAll()
                    }else{
                        view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestion, questionIndex))
                    }
                }else{
                    view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                }
            }else{
                Toast.makeText(activity, "Please choose one of the box", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


    private fun randomizeQuestion(){
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion(){
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = "Android trivial question ${questionIndex + 1} / $numQuestion"
    }

}
