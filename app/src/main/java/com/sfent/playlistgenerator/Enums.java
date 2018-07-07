package com.sfent.playlistgenerator;

public class Enums
{
    public enum ClauseComparisonType
    {
        STRING,
        INTEGER
    }

    public enum ClauseState
    {
        FIRST_CLAUSE,
        UNINITIALIZED,
        INITIALIZED,
        VALID
    }

    public enum ClauseSelectionType
    {
        AND,
        OR
    }

    public enum StringComparisonType
    {
        EQUALS,
        STARTS_WITH,
        ENDS_WITH,
        CONTAINS
    }

    public enum IntComparisonType
    {
        LESS_THAN,
        GREATER_THAN,
        BETWEEN,
        EQUALS
    }
}
