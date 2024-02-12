package entities;

import ch.qos.logback.core.joran.sanity.Pair;

public class Road {
    int id;
    int owner_id;
    Pair<Pair<Integer, Integer>,Pair<Integer, Integer>> vertecies; // t_id1, v_id1 -- t_id2, v_id2
}
