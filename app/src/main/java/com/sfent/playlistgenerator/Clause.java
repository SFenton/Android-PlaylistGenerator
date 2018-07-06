package com.sfent.playlistgenerator;

public class Clause
{
    private Enums.ClauseComparisonType p_clauseComparisonType;
    private Enums.ClauseSelectionType p_clauseSelectionType;
    private Enums.ClauseState p_clauseState;

    private String p_generatedSqlQuery;

    private int p_betweenLowerBoundary;
    private int p_betweenUpperBoundary;
    private int p_lessThanBoundary;
    private int p_greaterThanBoundary;
    private int p_equalsToBoundary;

    private String comparisonString;

    public Clause()
    {
        p_clauseState = Enums.ClauseState.UNINITIALIZED;
    }

    public Clause(Enums.ClauseState clauseState)
    {
        p_clauseState = clauseState;
    }

    public Clause(Enums.ClauseState clauseState, Enums.ClauseSelectionType selectionType)
    {
        p_clauseState = clauseState;
        p_clauseSelectionType = selectionType;
    }


}
