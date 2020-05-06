/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.dp3t.networking.models;

public class SdkConfigModel {

	private int numberOfWindowsForExposure;
	private float eventThreshold;
	private float badAttenuationThreshold;
	private float contactAttenuationThreshold;

	public int getNumberOfWindowsForExposure() {
		return numberOfWindowsForExposure;
	}

	public float getEventThreshold() {
		return eventThreshold;
	}

	public float getBadAttenuationThreshold() {
		return badAttenuationThreshold;
	}

	public float getContactAttenuationThreshold() {
		return contactAttenuationThreshold;
	}

	public void setNumberOfWindowsForExposure(int numberOfWindowsForExposure) {
		this.numberOfWindowsForExposure = numberOfWindowsForExposure;
	}

	public void setEventThreshold(float eventThreshold) {
		this.eventThreshold = eventThreshold;
	}

	public void setBadAttenuationThreshold(float badAttenuationThreshold) {
		this.badAttenuationThreshold = badAttenuationThreshold;
	}

	public void setContactAttenuationThreshold(float contactAttenuationThreshold) {
		this.contactAttenuationThreshold = contactAttenuationThreshold;
	}
}
