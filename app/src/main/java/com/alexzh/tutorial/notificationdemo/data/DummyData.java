package com.alexzh.tutorial.notificationdemo.data;

import com.alexzh.tutorial.notificationdemo.data.model.City;

import java.util.ArrayList;
import java.util.List;

public final class DummyData {

    private DummyData() {}

    // Resources of the data:
    // - https://en.wikipedia.org/wiki/Amsterdam
    // - https://en.wikipedia.org/wiki/Berlin
    // - https://en.wikipedia.org/wiki/Sydney
    // - https://en.wikipedia.org/wiki/Toronto
    public static List<City> getDummyData() {
        final List<City> notes = new ArrayList<>();
        notes.add(new City(
                1L,
                "Amsterdam",
                "Amsterdam is the capital of the Netherlands. Amsterdam had a population of 851,573 people in 2017.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Amsterdam_-_Rijksmuseum_-_panoramio_-_Nikolai_Karaneschev.jpg/640px-Amsterdam_-_Rijksmuseum_-_panoramio_-_Nikolai_Karaneschev.jpg"));
        notes.add(new City(
                2L,
                "Berlin",
                "Berlin is the capital and the largest city of Germany. Berlin had a population of 3,711,930 people in 2017.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Berlin_Skyline_Fernsehturm_02.jpg/640px-Berlin_Skyline_Fernsehturm_02.jpg"));
        notes.add(new City(
                3L,
                "Sydney",
                "Sydney is the state capital of New South Wales and the most populous city in Australia and Oceania. Sydney had a population of 5,131,326 people in 2017.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Sydney_skyline_from_the_north_August_2016_%2829009142591%29.jpg/640px-Sydney_skyline_from_the_north_August_2016_%2829009142591%29.jpg"));
        notes.add(new City(
                4L,
                "Toronto",
                "Toronto (officially the City of Toronto), is the capital of the Canadian province of Ontario. Toronto had a population of 2,731,571 people in 2016.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/York_after_sunset_%282855524410%29.jpg/640px-York_after_sunset_%282855524410%29.jpg"));
        return notes;
    }

    public static City getCityById(final long id) {
        return getDummyData().get((int) id - 1);
    }
}