package com.lunatk.ledmaskapp.objects

import java.io.Serializable

data class LEDMask(val nickname: String, val id: String, var did: String? = null, var token: String? = null): Serializable;