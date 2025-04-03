package brickset;

import java.util.*;
import java.util.stream.Collectors;

public class LegoSetImp implements LegoSetInterface{


    /**
     * @return list of 1000 legosets
     */
    @Override
    public List<LegoSet> getLegoSets() {

        return new LegoSetRepository().getAll()
                .stream().limit(1000).
                collect(Collectors.toList());
    }

    /**
     * Prints ascending order sorted themes where its tag has "Astronomy". Hint: There are tags that may be null
     */
    @Override
    public void printAllThemesByTag() {
       getLegoSets().stream()
               .filter(legoSet -> legoSet.tags() != null &&
                       legoSet.tags().contains("Astronomy"))
               .map(LegoSet::theme)
               .distinct()
               .sorted().forEach(System.out::println);
    }

    /**
     * Gets statistics according to Theme
     *
     * @param theme is given
     * @return summary statistics by theme
     */
    @Override
    public LongSummaryStatistics getSummaryStatisticsOfPiecesByTheme(String theme) {
        return getLegoSets().stream()
                .filter(legoSet -> theme.equals(legoSet.theme()))
                .mapToLong(LegoSet::pieces)
                .summaryStatistics();
    }

    /**
     * @return an average pieces by a theme "Icons"
     */
    @Override
    public Double getAvgPiecesOfIcons() {
        return getLegoSets().stream()
                .filter(legoSet -> legoSet.theme().contains("Icons"))
                .mapToLong(LegoSet::pieces)
                .average().orElse(0.0);
    }

    /**
     * Gets sum of pieces by theme
     *
     * @return Map of String(theme) and Integer(sum pieces)
     */
    @Override
    public Map<String, Integer> getSumOfPiecesByTheme() {
        return getLegoSets().stream()
                .collect(Collectors.groupingBy(
                        LegoSet::theme, Collectors.summingInt(LegoSet::pieces)
                ));
    }

    /**
     * @return Map of themes that mapped to sub themes that itself mapped to LegoSet
     */
    @Override
    public Map<String, Map<String, Set<LegoSet>>> getLegoSetByThemeThenBySubtheme() {
        return getLegoSets().stream()
                .filter(legoSet -> legoSet.subtheme() != null)
                .filter(legoSet -> legoSet.theme() != null)
                .collect(Collectors.groupingBy(LegoSet::theme,Collectors
                        .groupingBy(LegoSet::subtheme,Collectors.toSet())
                ));
    }

    public static void main(String[] args) {
        var lego = new LegoSetImp();
        //System.out.println(lego.getAll());
        //System.out.println(lego.getLegoSets());
        lego.printAllThemesByTag();
        System.out.println(lego.getSummaryStatisticsOfPiecesByTheme("Icons"));
        System.out.println(lego.getAvgPiecesOfIcons());
        System.out.println(lego.getSumOfPiecesByTheme());
        System.out.println(lego.getLegoSetByThemeThenBySubtheme());

    }
}
