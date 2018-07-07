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

    public Enums.ClauseComparisonType getP_clauseComparisonType() {
        return p_clauseComparisonType;
    }

    public void setP_clauseComparisonType(Enums.ClauseComparisonType p_clauseComparisonType) {
        if (this.p_clauseComparisonType == p_clauseComparisonType)
        {
            return;
        }

        p_betweenLowerBoundary = -1;
        p_betweenUpperBoundary = -1;
        p_lessThanBoundary = -1;
        p_greaterThanBoundary = -1;
        p_equalsToBoundary = -1;

        comparisonString = "";
        this.p_clauseComparisonType = p_clauseComparisonType;
    }

    public Enums.ClauseSelectionType getP_clauseSelectionType() {
        return p_clauseSelectionType;
    }

    public void setP_clauseSelectionType(Enums.ClauseSelectionType p_clauseSelectionType) {

        if (this.p_clauseSelectionType == p_clauseSelectionType)
        {
            return;
        }

        this.p_clauseSelectionType = p_clauseSelectionType;
    }

    public Enums.ClauseState getP_clauseState() {
        return p_clauseState;
    }

    public void setP_clauseState(Enums.ClauseState p_clauseState) {
        this.p_clauseState = p_clauseState;
    }


    public int getP_betweenLowerBoundary() {
        return p_betweenLowerBoundary;
    }

    public void setP_betweenLowerBoundary(int p_betweenLowerBoundary) {
        this.p_betweenLowerBoundary = p_betweenLowerBoundary;
    }

    public int getP_betweenUpperBoundary() {
        return p_betweenUpperBoundary;
    }

    public void setP_betweenUpperBoundary(int p_betweenUpperBoundary) {
        this.p_betweenUpperBoundary = p_betweenUpperBoundary;
    }

    public int getP_lessThanBoundary() {
        return p_lessThanBoundary;
    }

    public void setP_lessThanBoundary(int p_lessThanBoundary) {
        this.p_lessThanBoundary = p_lessThanBoundary;
    }

    public int getP_greaterThanBoundary() {
        return p_greaterThanBoundary;
    }

    public void setP_greaterThanBoundary(int p_greaterThanBoundary) {
        this.p_greaterThanBoundary = p_greaterThanBoundary;
    }

    public int getP_equalsToBoundary() {
        return p_equalsToBoundary;
    }

    public void setP_equalsToBoundary(int p_equalsToBoundary) {
        this.p_equalsToBoundary = p_equalsToBoundary;
    }

    public String getComparisonString() {
        return comparisonString;
    }

    public void setComparisonString(String comparisonString) {
        this.comparisonString = comparisonString;
    }
}
