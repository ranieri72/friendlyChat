/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat.model;

public class FriendlyMessage {

    private String text;
    private Friends sender;
    private Friends recipient;
    private String photoUrl;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, Friends sender, Friends recipient, String photoUrl) {
        this.text = text;
        this.sender = sender;
        this.recipient = recipient;
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Friends getSender() { return sender; }

    public void setSender(Friends sender) { this.sender = sender; }

    public Friends getRecipient() { return recipient; }

    public void setRecipient(Friends recipient) { this.recipient = recipient; }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
