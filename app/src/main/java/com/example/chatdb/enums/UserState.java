package com.example.chatdb.enums;

/**
 * Information about who the user is to current (authorized) user
 */
public enum UserState {
    NOTHING,
    FRIEND,
    INCOMING_REQUEST,
    OUTGOING_REQUEST
}
