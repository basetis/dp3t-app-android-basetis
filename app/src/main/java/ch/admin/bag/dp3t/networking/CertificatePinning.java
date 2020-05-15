/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.dp3t.networking;

import okhttp3.CertificatePinner;

public class CertificatePinning {

	private static CertificatePinner instance;

	public static CertificatePinner getCertificatePinner() {
		if (instance == null) {
			instance = new CertificatePinner.Builder()
					.add("dpppt.test.basetis.com", "sha256/kOJhThKcZpNnCGtCp7eKbH1oe0WlglLZseDojs4CSYM=") // leaf
					.build();
		}
		return instance;
	}

}
