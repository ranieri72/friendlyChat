/**
 * Copyright Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat.model;

import com.google.firebase.database.Exclude;

public class FriendlyMessage {

    @Exclude
    private String id;
    private String text;
    private String senderRecipient;
    private String photoUrl;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, User sender, User recipient, String photoUrl) {
        this.text = text;
        this.senderRecipient = sender.getId() + "_" + recipient.getId();
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderRecipient() {
        return senderRecipient;
    }

    public void setSenderRecipient(String senderRecipient) {
        this.senderRecipient = senderRecipient;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}