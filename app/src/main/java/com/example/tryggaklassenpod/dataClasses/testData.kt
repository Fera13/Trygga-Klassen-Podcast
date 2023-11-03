
package com.example.tryggaklassenpod.dataClasses

val episodesList: List<Episode> = listOf(
    Episode(
        //id = 0,
        imageUrl = "https://cdn.pixabay.com/audio/2022/05/27/23-51-43-941_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/05/27/audio_1808fbf07a.mp3?filename=lofi-study-112191.mp3",
        duration = 147,
        title = "Episode Title 1",
        description = "Discover thrilling adventures and unexpected twists in this gripping novel that keeps you on the edge of your seat until the very last page.",
        comments = listOf(
            Comments(
                //commentId = 0,
                comment = "OMG crazy story, this sucks",
                author = "HeyThere",
                createdAt = 1633873800000L,
                approved = false,
                likes = 0
            ),
            Comments(
                //commentId = 1,
                comment = "Great story!",
                author = "Reader123",
                createdAt = 1633873810000L,  // A different timestamp for the second comment
                approved = true,
                likes = 10
            ),
            Comments(
                //commentId = 2,
                comment = "Interesting plot twists!",
                author = "BookLover456",
                createdAt = 1633873820000L,
                approved = true,
                likes = 5
            ),
            Comments(
                //commentId = 3,
                comment = "Looking forward to the sequel!",
                author = "ExcitedReader",
                createdAt = 1633873830000L,
                approved = true,
                likes = 7
            )
        ),
        categories = listOf(
            "Adventure",
            "Mystery",
            "Horror",
            "Drama",
            "Biography",
            "Comedy",
            "Self-Help",
            "Young Adult"
        )
    ),
    Episode(
        //id = 1,
        imageUrl = "https://cdn.pixabay.com/audio/2023/03/02/23-27-55-753_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2023/03/02/audio_501a4aea8a.mp3?filename=genres-hiphop-lofi-141320.mp3",
        duration = 765,
        title = "Episode Title 2",
        description = "Immerse yourself in a world of magic and wonder as you follow a young hero's quest to save the kingdom from an ancient evil threatening its existence.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 2,
        imageUrl = "https://cdn.pixabay.com/audio/2022/09/02/21-42-13-13_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/09/02/audio_72502a492a.mp3?filename=empty-mind-118973.mp3",
        duration = 175,
        title = "Episode Title 3",
        description = "In a post-apocalyptic wasteland, a group of survivors must navigate danger and betrayal to find a new home, hoping for a chance at rebuilding society.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 3,
        imageUrl = "https://cdn.pixabay.com/audio/2022/05/27/23-51-43-941_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/05/27/audio_1808fbf07a.mp3?filename=lofi-study-112191.mp3",
        duration = 147,
        title = "Episode Title 4",
        description = "Explore the complexities of human relationships and the power of love in this heartwarming tale that will make you laugh, cry, and ponder life's mysteries.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 4,
        imageUrl = "https://cdn.pixabay.com/audio/2023/03/02/23-27-55-753_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2023/03/02/audio_501a4aea8a.mp3?filename=genres-hiphop-lofi-141320.mp3",
        duration = 765,
        title = "Episode Title 5",
        description = "Uncover the secrets of a small, close-knit community where everyone has something to hide, and the truth might just shatter their peaceful facade.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 5,
        imageUrl = "https://cdn.pixabay.com/audio/2022/09/02/21-42-13-13_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/09/02/audio_72502a492a.mp3?filename=empty-mind-118973.mp3",
        duration = 175,
        title = "Episode Title 6",
        description = "Embark on a culinary journey through exotic flavors and enticing aromas as you follow a chef's quest for perfection and success in the culinary world.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 6,
        imageUrl = "https://cdn.pixabay.com/audio/2022/05/27/23-51-43-941_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/05/27/audio_1808fbf07a.mp3?filename=lofi-study-112191.mp3",
        duration = 147,
        title = "Episode Title 7",
        description = "Discover thrilling adventures and unexpected twists in this gripping novel that keeps you on the edge of your seat until the very last page.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 7,
        imageUrl = "https://cdn.pixabay.com/audio/2023/03/02/23-27-55-753_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2023/03/02/audio_501a4aea8a.mp3?filename=genres-hiphop-lofi-141320.mp3",
        duration = 765,
        title = "Episode Title 8",
        description = "Immerse yourself in a world of magic and wonder as you follow a young hero's quest to save the kingdom from an ancient evil threatening its existence.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 8,
        imageUrl = "https://cdn.pixabay.com/audio/2022/09/02/21-42-13-13_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/09/02/audio_72502a492a.mp3?filename=empty-mind-118973.mp3",
        duration = 175,
        title = "Episode Title 9",
        description = "In a post-apocalyptic wasteland, a group of survivors must navigate danger and betrayal to find a new home, hoping for a chance at rebuilding society.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 9,
        imageUrl = "https://cdn.pixabay.com/audio/2022/05/27/23-51-43-941_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/05/27/audio_1808fbf07a.mp3?filename=lofi-study-112191.mp3",
        duration = 147,
        title = "Episode Title 10",
        description = "Explore the complexities of human relationships and the power of love in this heartwarming tale that will make you laugh, cry, and ponder life's mysteries.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 10,
        imageUrl = "https://cdn.pixabay.com/audio/2023/03/02/23-27-55-753_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2023/03/02/audio_501a4aea8a.mp3?filename=genres-hiphop-lofi-141320.mp3",
        duration = 765,
        title = "Episode Title 11",
        description = "Uncover the secrets of a small, close-knit community where everyone has something to hide, and the truth might just shatter their peaceful facade.",
        comments = listOf(),
        categories = listOf()
    ),
    Episode(
        //id = 11,
        imageUrl = "https://cdn.pixabay.com/audio/2022/09/02/21-42-13-13_200x200.jpg",
        episodeUrl = "https://cdn.pixabay.com/download/audio/2022/09/02/audio_72502a492a.mp3?filename=empty-mind-118973.mp3",
        duration = 175,
        title = "Episode Title 12",
        description = "Embark on a culinary journey through exotic flavors and enticing aromas as you follow a chef's quest for perfection and success in the culinary world.",
        comments = listOf(),
        categories = listOf()
    ),
)
