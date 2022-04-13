package com.example.rickandmorty.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.preference.PreferenceManager
import com.example.rickandmorty.NavGraphDirections
import com.example.rickandmorty.R
import com.example.rickandmorty.application.RickAndMortyApplication
import com.example.rickandmorty.databinding.RickAndMortyListFragmentBinding
import com.example.rickandmorty.ui.setting.MySettingsFragment

class RickAndMortyListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels{
        MainViewModel.MainViewModelFactory((activity?.application as RickAndMortyApplication).repository)
    }
    private var _binding: RickAndMortyListFragmentBinding ? = null
    private val binding
             get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RickAndMortyListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

           // viewModel.getCharacter("2")
            val adapter = CharactersAdapter()
            rickAndMortyRecyclerView.adapter = adapter.withLoadStateFooter(
                CharacterLoadStateAdapter{adapter.retry()}
            )



           viewModel.characterPaging.observe(viewLifecycleOwner)//,adapter::submitData)
           {
                adapter.submitData(viewLifecycleOwner.lifecycle , it )
            }


        }


        PreferenceManager
            .setDefaultValues(requireContext(), R.xml.setting_rick_and_morty, false)

        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val switchPref = sharedPref.getBoolean(MySettingsFragment.KEY_PREF_EXAMPLE_SWITCH, true)
        Toast.makeText(
            requireContext(), switchPref.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId==R.id.about_app)
        {
            val action = NavGraphDirections.actionGlobalAboutAppFragment()
            findNavController().navigate(action)

            val storage = activity?.getPreferences(AppCompatActivity.MODE_PRIVATE)
            val number =storage?.getInt("number",0)
            Toast.makeText(requireContext(),number.toString(),Toast.LENGTH_SHORT).show()
            return true
        }


        return item.onNavDestinationSelected(navController = findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}