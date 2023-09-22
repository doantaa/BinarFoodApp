package com.binar.binarfoodapp.data

import com.binar.binarfoodapp.model.Food

interface FoodDataSource {
    fun getFoodData() : List<Food>
}

class FoodDataSourceImpl(): FoodDataSource {
    override fun getFoodData(): List<Food> {
        return mutableListOf(
            Food(
                name = "Ayam Goreng",
                price = 50000,
                description = "Ini adalah ayam yang digoreng dengan minyak pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ayam_goreng.png"
            ),

            Food(
                name = "Ayam Bakar",
                price = 45000,
                description = "Ini adalah ayam bakar yang dibakar dengan api pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ayam_bakar.png"
            ),

            Food(
                name = "Ayam Geprek",
                price = 30000,
                description = "Ini adalah ayam goreng yang digeprek dengan batu pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ayam_geprek.png"
            ),

            Food(
                name = "Bakso",
                price = 44000,
                description = "Ini adalah bakso yang digiling dengan daging pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/bakso.png"
            ),

            Food(
                name = "Nasi Campur",
                price = 80000,
                description = "Ini adalah Nasi yang dicampur pakai beras pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/nasi_campur.png"
            ),

            Food(
                name = "Sate Usus",
                price = 15000,
                description = "Ini adalah Sate usus yang ditusuk pakai lidi pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/sate_usus.png"
            ),

            Food(
                name = "Pizza Rames",
                price = 5000,
                description = "Ini adalah pizza yang dibuat dengan campuran nasi rames membuat anda menikmati karbohidrat ganda saat gigitan pertama",
                imageUrl = "https://lh3.googleusercontent.com/fife/AKsag4OqGUyp45EPWKkAOp0w_Pzj9q_T7IIQbkrx__-mkhH-Xq4hPKh2BMFrUXIN12cXcQWwY3R5rGxQGkiOpv-if17XzwdconmonNnOQd6Kdv1k6kdWUQ52PdasXKo0eM52qju_ogaEYTw0bB9yF-LNFVBC1HIAnSqHhngubla-qgKcPQiqfakBF90dJJjraeo-dDUC_OWADWTQIAjK071Qj0CvWQ5UbdIRpuo6haDZ7ilukY8i0zn2YmW37sIFdINA3EnNzlsf4vQSFyBnh-vPwfnsTCo9poBM5rlCv7HdbzMbJegbmSFm6qkER7neotBzaPMpF-d0MexL8JdrDAitSqtAqicr3MJ3BS81gyAm_5UvpprLCtRaAkh_EYd7Lk32SQrugAcaZf8fQPUMhketzQeUSu2aqzHwMnjT1aeLwROSUJsTfi6vg9Q9XCk4UIkaY0U14PD07QGf8iuOaFcSJwi7zJgsI2have-Ykx6jbqExs6AN0RTZAuWm4KlwseMCmH34f3IWJRArZd8ZUspMWUD5KJC56Dln8q3zyBKLeGA2v-tt7e7zWPcri0-qySJRK2I-cal96usjKqL3i9q7MaZLVlzLkoATyw9zWtfl-rgsoHK8blqG1Q8NsY2pxApMkNfIgxlR-nJsMX2ADIKh5RNp5n9wQmthGNgml2Ev9p83VhTJXta3wlOyrqKRo9xn9L2FR__mU29SFqniphwTVxC2wlEuqUHbXpjDUCQRZ82VA0bK_q_eX2cbKwbbrR3UCGCjKx_SnCxx_weOmijIaNxFpi7a1tKAOl9FbRkVAjgmbNBlBNQSzs2J3te5rdOruPpH__5gvMhy1Zt9LfXwZCVnSMPbBEVvHyTzqj28l5Xan72ZfdWT8k3HxUop6B6Z5AJfSrrT7esh2dVjkfa3ECEfPYKZTdIXE4VmbpDUnm8oqSIoMewQdIa0FRwA1LIvZAXEB03fhek4c0NYeKSG6E-tkCAvlDAmd6Zfxeqydc2fJKJ-WT8HVC_E2EzBYjxxMIpwmFwCnxHNzLCAbtH6sxL7y4dag6dcVvhIPMDcSEI0sK7MouP6rr-_J1Oj8QHufiZC-hd91OaFN1vQABsh7iXNGxcrDF52NPCjd8bXhcCgs2l0yb7oKm2DDjSZ_iOxGdDMoTninxdlfW0Wy10zPkO2NsFYcK-_TXSOHRdmZSowEjW66_icrT4NdlHRqJ8vu3OQYoIYuPJXKbO8c6Tqwiw1_EWqJTjbwLhsXP8khb21EQntX7k02IaKVrh1-y9OVvgSgjRHxg6b-HPgFeZ99mF3mJjtOR3X1Qbsw-aECs7KrjvLhnEGrADHtem79j_bKWpEEa0NKowqXXcme-3ESU6GGJB6_YoLc-QTBt2eAyS4NMdAHNmZL6dBlOnXzgF_d3aL3stdJ4FvIruJZYlpDLk0UjvX1CHHHm1SmcAXJqtBhmS12FaywXZXQ0tgHS95fzgQZAvKimGMUNXlFaHVwwl12GeCiP4MuvVLofW7iys_0k4Q7viRirADXvYVClHiLz77o68Vj5M1uJ-mud9VX0_ahfwUsf1prTbM_9rZKiDrHxQZoIjC375kvA=w1920-h883"
            ),

            Food(
                name = "Kopi bergambar",
                price = 5000,
                description = "Digambar pakai cetakan late yang dihadirkan langsung dari depok, menggunakan kopi kapal api dan susu nasional. Bonus koasong selama persediaan masih ada",
                imageUrl = "https://lh3.googleusercontent.com/fife/AKsag4OZFjgA2MxG3XYueHm87oNrpkqsAGSUfHD82ASKXMH0iRqeiz1icVRJ2Bv1-ySZziayE-1Lk4fJJphog7_UgEdq4QEKKDTYdwS6IEioCvdqEzUxmZpX-iuoApoXbk1KbSLPZ8ORkZIuUdSZqOTQ6MNjp2r5Kcnf_TCZ1hAibz1LDGBRFwn_RFBuS8ylqxxb2vYuE9bOzc2zTSTifLHR3dn1cUHyznv1aO3dpycL3g-JXPecrWIq7FBDaKcXVl7_stsgAXsuLDfNuFe9NbKMWhfd3MBny1rOojqwfQnBZJl92UA44cuqmFLeTF7IB2FzIpAOy7ERJZMkDtC8hr-wYk0ApeC2AEjIAZJ2FkhcqgupaNdtUDH4X0N3EGcNJh5q2lzEC8Bb7zKStbT9M5dk2-_Xl71VQoUKiXUUgPUG8AI3PbnlxTLsu18JZd7l0KsfjTyPD_ZdR_dC0l1DQ2vQg4LYKYBxbuR8FDQxbdhhMsR1VCJ0Kv7QqLqeD4Tcc7TZkofeXdFelTLiXAzNpPkTe17rEv35ygxqyoKx-VoPg5YKzQfse7mEQ5bhPeFtayWMpZp9I7rRVmZtfKJoCTIfAWvYr8xkzfqInB-t4OS0O1GM6qNll2XbeqqWJXa-UOnoeCYHJbWz0SVOAD_Wee1wGMQy3BQoXoNzL_3ObdSQqJZaMJ_9sWOVsQmX_XYijQg3pz9BHdQMT2y01dFie7OHNy6cfIy6zk0zgeu_lI9ecKWWJ8q9dygoTocgaK627gR39s6G9xoGmOGxiUaD7rj-KoU0jzsbllY887pnFK0mBERSVloZZpCBeV7MTLcyVk-zRfeUeWpn8vGw21fNHPQ2tJ-DaA0397ycGCIuHOxU1Fy96Fd_6G05t0Lm894EA9Wa7jIcUPXM6h00G4D_B8UhNLjwuXzOfcSGWJfbsAzK2rDnkIa9bRa66iZW0DOX01nXfEdYU2N_9FWU1RltiIGP4o47kl0udLuXT-4TQVTLPsDBqgLYe4p6pu3nWxQJaSq3VHJfvFuWfxPkj-Sn_mb0XzJlYrLtL2wEdx2UYvyadmRSObG3BPbj642np043jLHY1lUMMtKqGTSXXTY6_s-BpCwr4SAv3Bgxi5pwjNijlslsA2nafgOJD2COn_6daKnqz3EnubSbbRY5balm-HormxZbNTp6vTKY_UbBnx0DRu04KqDrEwdkUqJWtIOf1DDpgRUk8sSD9Mg1oKitnGAMk_GGBlh0wQbeY6jbnOwCH2aV0nXvmMnQxw6elGRTpBKm0NKVKr-Ur5BDyKPCxyY6jJ1NahVV9lorUZWVUbORB0DrjARB3bbwwIZS2odzYTuKIcFmY1q6gsH45sySkN91-dgYCfWKpl3PaNxS_MhAC6V-vG32w_yo66dH6px0OGKGuKRnSeqCNWsKueIb2h_pmbUTGESdYcQNrDPMq_czgADG-_jF6_KoPGNNZrAQSgJEP0ax8ldkvXyKSBeG7mjkk5o7yanjv4oYN99cg2xdB0WVKvhAbDprj4GSgsnUv06WwskMCEz0XMmxSfG7yZGuc6OjFBXjI1OuGp1wC-NIxngl5vJLUKdnucbQqg=w1920-h480"
            )
        )
    }
}