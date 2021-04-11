package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.ElevatorRepository;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class MainFragmentViewModel extends AndroidViewModel {
    private ElevatorRepository repository;
    private LiveData<List<ElevatorItem>> allElevators;
    private Random rand = new Random();

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new ElevatorRepository(application);
        allElevators = repository.getAllElevators();
    }

    /**
     * Metoda obsługująca bazę danych
     */
    private void upsert(ElevatorItem item) {
        repository.upsert(item);
    }

    /**
     * Metoda obsługująca bazę danych. Zadeklarowana jako publiczna ponieważ używana jest w MainFragmencie.
     */
    public void deleteAllElevators() {
        repository.deleteAllElevators();
    }

    /**
     * Metoda obsługująca bazę danych. Zadeklarowana jako publiczna ponieważ używana jest w MainFragmencie.
     */
    public LiveData<List<ElevatorItem>> getAllElevators() {
        return allElevators;
    }

    /**
     * Metoda generująca jedna z danych do symualacji. Zwraca liczbę całkowitą z zakresu od -1 do 1.
     * Zgodnie z założeniem -1 oznacza kierunek windy w dół,
     * 0 oznacza, że winda stoi i jest pusta,
     * a 1 oznaca, że winda porusza się do góry
     */
    public int generateRandomState() {
        return (rand.nextInt(3) - 1);
    }

    /**
     * Metoda generująca jedna z danych do symualacji. Zwraca liczbę całkowitą z zakresu od 0 do maksymalnej liczby pięter.
     */
    public int generateRandomLevel(int maxLevel) {
        return rand.nextInt(maxLevel + 1);
    }

    /**
     * Metoda aktualizająca listę wind(jedną z wind) zgodnie z założeniami systemu. Wywoływana w pętli w MainFragment odpowiada za generowanie znaczących danych do symulacji.
     * Przyjąłem, że winda może przyjąć max 6 osób, stąd losowana kolejka ma 6 elementów. Winda może przyjąć kolejkę pięter większą niż 6, lecz symulacja generuje maksymalnie kolejkę
     * sześciu pięter co jest naturalne podczas używania wind przez prawdziwych użytkowników. W zależności od kierunku jazdy windy losuję liczbę pięter w kolejce, a następnie losuję
     * piętra do kolejki tyle razy ile kolejka posiada w założeniu miejsc. Losowanie pięter odbywa się na podstawie zakresu od obecnego poziomu w górę lub w dół (w zależności od kierunku).
     */
    public void updateItem(int id, int currLevel, int maxLevel, int state) {
        ArrayList<String> orderLevels = new ArrayList<>();
        if (state == -1 && currLevel == 0)
            state = 0;
        if (state == 1 && currLevel == maxLevel)
            state = 0;
        if (orderLevels.size() > 0) orderLevels.clear();
        switch (state) {
            case -1:
                int ordersDown = rand.nextInt(6) + 1;
                for (int i = 0; i < ordersDown; i++) {
                    orderLevels.add(String.valueOf(rand.nextInt(currLevel)));
                }
                break;
            case 1:
                int ordersUp = rand.nextInt(6) + 1;
                for (int i = 0; i < ordersUp; i++) {
                    int a = currLevel + rand.nextInt(maxLevel - currLevel) + 1;
                    orderLevels.add(String.valueOf(a));
                }
                break;
        }
        if (!(orderLevels.size() > 0)) {
            orderLevels.add("-1");
            upsert(new ElevatorItem(id, currLevel, orderLevels, maxLevel, state));
        } else {
            sortList(orderLevels, state);
            upsert(new ElevatorItem(id, currLevel, orderLevels, maxLevel, state));
        }

    }

    /**
     * Zdecydowanie nieidealna metoda sortująca, lecz w pełni funkcjonalna. Ze względu na trudności w posortowaniu listy stringów,
     * gdzie np. po operacji sortowania "11" było przed "2". Zdecydowałem się na poniższe rozwiązanie, gdzie Stringi przekazuję do listy z Integerami,
     * a następnie używam kolekcji Set, która w prosty sposób eliminuje powtórzenia. Ostatnim krokiem jest posortowanie listy zależnie od kierunku poruszania
     * się windy oraz powrót do listy ze Stringami.
     */
    private void sortList(ArrayList<String> orderLevels, int state) {
        List<Integer> sortedList = new ArrayList<>();
        for (String item : orderLevels) sortedList.add(Integer.parseInt(item));
        orderLevels.clear();
        //The set will skip repeats automatically
        Set<Integer> set = new HashSet<>(sortedList);
        sortedList.clear();
        sortedList.addAll(set);
        if (state == -1) Collections.sort(sortedList, Collections.reverseOrder());
        if (state == 1) Collections.sort(sortedList);
        for (int item : sortedList) orderLevels.add(String.valueOf(item));
    }


}
