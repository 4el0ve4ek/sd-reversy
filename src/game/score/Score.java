package game.score;

import game.cell.CellType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Score {
    private final Map<CellType, Integer> data = new HashMap<>();

    public Score() {
    }

    public Score(Map<CellType, Integer> data) {
        this.data.putAll(data);
    }

    public void extend(Map<CellType, Integer> data) {
        data.forEach((type, result) -> {
            var oldValue = this.data.get(type);
            if (!this.data.containsKey(type) || oldValue < result) {
                this.data.put(type, result);
            }
        });
    }

    public void extend(Score score) {
        extend(score.data);
    }

    public List<CellType> keys() {
        return new ArrayList<>(data.keySet());
    }

    public Integer get(CellType cellType) {
        return data.get(cellType);
    }

    public CellType getWinner(){
        var result = CellType.NOTHING;
        for(var cellType : keys()) {
            if(result == CellType.NOTHING || get(result) < get(cellType)) {
                result = cellType;
            }
        }
        return result;
    }
}
