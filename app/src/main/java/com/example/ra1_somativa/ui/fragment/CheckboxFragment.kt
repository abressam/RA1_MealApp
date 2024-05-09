package com.example.ra1_somativa.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.example.ra1_somativa.R
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.presentation.MealViewModel
class CheckboxFragment : Fragment() {

    private lateinit var viewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MealViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_checkbox, container, false)

        // Referenciar o LinearLayout onde os CheckBoxes serão adicionados
        val containerLayout = view.findViewById<LinearLayout>(R.id.container_checkboxes)

        // Observar os dados do ViewModel
        viewModel.mealDetailsInfo.observe(viewLifecycleOwner) { mealDetailsList ->
            mealDetailsList?.let { displayCheckBoxes(containerLayout, it) }
        }

        return view
    }

    private fun displayCheckBoxes(containerLayout: LinearLayout, mealDetailsList: List<MealDetails>) {
        // Percorrer a lista de MealDetails para obter os ingredientes
        mealDetailsList.forEach { mealDetails ->
            for (i in 1..20) { // Iterar sobre os 20 possíveis ingredientes
                val ingredientFieldName = "strIngredient$i"
                val ingredientValue = getIngredientValue(mealDetails, ingredientFieldName)

                // Verificar se o valor do ingrediente não é uma string vazia antes de criar o CheckBox
                if (!ingredientValue.isNullOrBlank()) {
                    val checkBox = CheckBox(requireContext())
                    checkBox.text = ingredientValue
                    containerLayout.addView(checkBox) // Adicionar CheckBox ao LinearLayout
                }

            }
        }
    }

    private fun getIngredientValue(mealDetails: MealDetails, fieldName: String): String? {
        return when (fieldName) {
            "strIngredient1" -> concatenateIngredient(mealDetails.strIngredient1, mealDetails.strMeasure1)
            "strIngredient2" -> concatenateIngredient(mealDetails.strIngredient2, mealDetails.strMeasure2)
            "strIngredient3" -> concatenateIngredient(mealDetails.strIngredient3, mealDetails.strMeasure3)
            "strIngredient4" -> concatenateIngredient(mealDetails.strIngredient4, mealDetails.strMeasure4)
            "strIngredient5" -> concatenateIngredient(mealDetails.strIngredient5, mealDetails.strMeasure5)
            "strIngredient6" -> concatenateIngredient(mealDetails.strIngredient6, mealDetails.strMeasure6)
            "strIngredient7" -> concatenateIngredient(mealDetails.strIngredient7, mealDetails.strMeasure7)
            "strIngredient8" -> concatenateIngredient(mealDetails.strIngredient8, mealDetails.strMeasure8)
            "strIngredient9" -> concatenateIngredient(mealDetails.strIngredient9, mealDetails.strMeasure9)
            "strIngredient10" -> concatenateIngredient(mealDetails.strIngredient10, mealDetails.strMeasure10)
            "strIngredient11" -> concatenateIngredient(mealDetails.strIngredient11, mealDetails.strMeasure11)
            "strIngredient12" -> concatenateIngredient(mealDetails.strIngredient12, mealDetails.strMeasure12)
            "strIngredient13" -> concatenateIngredient(mealDetails.strIngredient13, mealDetails.strMeasure13)
            "strIngredient14" -> concatenateIngredient(mealDetails.strIngredient14, mealDetails.strMeasure14)
            "strIngredient15" -> concatenateIngredient(mealDetails.strIngredient15, mealDetails.strMeasure15)
            "strIngredient16" -> concatenateIngredient(mealDetails.strIngredient16, mealDetails.strMeasure16)
            "strIngredient17" -> concatenateIngredient(mealDetails.strIngredient17, mealDetails.strMeasure17)
            "strIngredient18" -> concatenateIngredient(mealDetails.strIngredient18, mealDetails.strMeasure18)
            "strIngredient19" -> concatenateIngredient(mealDetails.strIngredient19, mealDetails.strMeasure19)
            "strIngredient20" -> concatenateIngredient(mealDetails.strIngredient20, mealDetails.strMeasure20)
            else -> null
        }
    }

    private fun concatenateIngredient(ingredient: String?, measure: String?): String? {
        if (ingredient.isNullOrBlank()) {
            return null
        }

        val ingredientWithMeasure = if (!measure.isNullOrBlank()) {
            "$ingredient - $measure"
        } else {
            ingredient
        }

        return ingredientWithMeasure
    }
}