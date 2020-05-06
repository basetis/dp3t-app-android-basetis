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

public class ConfigResponseModel {

	private boolean forceUpdate;
	private boolean forceTraceShutdown;
	private InfoBoxModelCollection infoBox;
	private SdkConfigModel sdkConfig;

	public boolean getDoForceUpdate() {
		return forceUpdate;
	}

	public boolean getForceTraceShutdown() {
		return forceTraceShutdown;
	}

	public InfoBoxModelCollection getInfoBox() {
		return infoBox;
	}

	public InfoBoxModel getInfoBox(String languageKey) {
		return infoBox.getInfoBox(languageKey);
	}

	public SdkConfigModel getSdkConfig() {
		return sdkConfig;
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public boolean isForceTraceShutdown() {
		return forceTraceShutdown;
	}

	public void setForceTraceShutdown(boolean forceTraceShutdown) {
		this.forceTraceShutdown = forceTraceShutdown;
	}

	public void setInfoBox(InfoBoxModelCollection infoBox) {
		this.infoBox = infoBox;
	}

	public void setSdkConfig(SdkConfigModel sdkConfig) {
		this.sdkConfig = sdkConfig;
	}
}
