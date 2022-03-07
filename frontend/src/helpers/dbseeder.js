import Api from "../Api";

const podcasts = [
  {
    name: "Brain Sparks",
    description:
      "This podcast is hosted by usability and UI design expert, Jared Spool. It offers tona of value with many industry leaders sharing insights on usability, UX design, and UI design.",
    source: "https://www.uie.com/brainsparks/topics/podcasts/",
    audio:
      "https://downloads.uie.fm/7/389/asset.uie.com/BSAL/UIEP015_Shipe_Goodwin.mp3",
    image: "https://uie.fm/assets/album-art/the-uie-podcast.png",
    title:
      "The Tension of Art and Science When Communicating Complex User Research",
  },
  {
    name: "The Dirt Show",
    description:
      'The Dirt Show is a podcast hosted by the team behind the UX design agency, Fresh Tilled Soil. One cool thing about this podcast is some topics follow a mini-series format, with multiple episodes and different guests talking about the same topic. For example, they have a mini-series on topics such as "designing your career" and "designing the future".',
    source: "http://www.freshtilledsoil.com/category/podcast/",
    audio: "http://traffic.libsyn.com/thedirt/James_Aylward_-_Final.mp3",
    image: "",
    title: "Data-Driven Design with James Aylward of Pluralsight",
  },
  {
    name: "UX Podcast",
    description:
      "UX Podcast is for people passionate about balancing business, technology and users within the realm of digital media. Moving the conversation beyond the traditional realm of User Experience.",
    source: "http://uxpodcast.com/",
    audio:
      "https://content.blubrry.com/uxpodcast/uxpodcast-episode-253-derek-featherstone.mp3",
    image:
      "https://3u9ouc3rh80mit7dp2jeoxqa-wpengine.netdna-ssl.com/wp-content/uploads/2020/12/253-derek-featherstone-uxpodcast-logo-gubben-768x768.jpg",
    title: "The state of accessibility with Derek Featherstone",
  },
  {
    name: "UX Pod",
    description:
      "Gerry Gaffney set up the User Experience podcast in 2006, with a view to providing a platform for discussing topics of interest to practitioners or others with an interest in UX.",
    source: "http://uxpod.com/",
    audio: "https://traffic.libsyn.com/uxpod/RoryMadden.mp3",
    image: "https://uxpod.com/wp-content/uploads/2020/12/street.jpg",
    title: "Running a virtual conference: An interview with Rory Madden\n",
  },
  {
    name: "UX Mastery",
    description:
      "UX Mastery is a website that hosts a lot of useful articles and content related to user experience design. The site also has an infrequently updated podcast.",
    source: "http://uxmastery.com/category/podcast/",
    audio:
      "https://anchor.fm/s/22de0dec/podcast/play/14166520/https%3A%2F%2Fd3ctxlq1ktw2nl.cloudfront.net%2Fproduction%2F2020-4-23%2F75875188-44100-2-5e28660fb7dbf.mp3",
    image:
      "https://uxmastery.com/wp-content/uploads/2016/03/tom-greever-feature.jpg",
    title: "Design Decisions with Tom Greever",
  },
  {
    name: "UX Discovery Session",
    description:
      "A discovery session is an opportunity to learn about a relevant topic, generate ideas that expand a new technique or practice, or brainstorm concepts and ideas for the next new thing.",
    source: "http://uxdiscoverysession.com/",
    audio:
      "http://traffic.libsyn.com/discoverysession/UXDS071_Di_Dang_New_Technologies_Design_Advocate_at_Google.mp3",
    image: "",
    title: "Di Dang, New Technologies Design Advocate at Google",
  },
  {
    name: "React Podcast",
    description: "Conversations about React with your favorite developers.",
    source: "https://reactpodcast.simplecast.com/",
    audio:
      "https://cdn.simplecast.com/audio/bdb43d4d-bd1d-4fbc-bd60-40f1e3299aa3/episodes/a6363455-0a0e-460f-8074-75f4f5e84775/audio/620d2586-389b-44c7-80ad-25703fd52094/default_tc.mp3",
    image:
      "https://image.simplecastcdn.com/images/bdb43d4d-bd1d-4fbc-bd60-40f1e3299aa3/20f97d0b-6dab-4005-a995-0ab8fc2cf31c/300x300/1550288890-artwork@2x.jpg",
    title: "Cassidy Williams on Dreams and Disasters in 2020",
  },
  {
    name: "Javascript Jabber",
    description:
      "Weekly podcast discussion about Javascript on the front and back ends.",
    source: "https://devchat.tv/podcasts/js-jabber/",
    audio:
      "https://media.devchat.tv/js-jabber/JSJ_460_The_Things_Every_JavaScript_Developer_Must_Know_Part_2.mp3",
    image:
      "https://devchat.tv/wp-content/uploads/2020/06/js_jabber-534x534.jpg",
    title: "The Things Every JavaScript Developer Must Know, Part 2",
  },
  {
    name: "JS Party",
    description: "a weekly celebration of JavaScript and the web",
    source: "https://changelog.com/jsparty",
    audio: "https://cdn.changelog.com/uploads/jsparty/89/js-party-89.mp3",
    image:
      "https://cdn.changelog.com/uploads/covers/js-party-original.png?v=63725770332",
    title: "Modern JS tooling is too complicated. Yep? Nope?",
  },
  {
    name: "The Changelog",
    description:
      "Conversations with the hackers, leaders, and innovators of the software world",
    source: "https://changelog.com/podcast/352",
    audio:
      "https://cdn.changelog.com/uploads/podcast/352/the-changelog-352.mp3",
    image:
      "https://cdn.changelog.com/uploads/covers/the-changelog-original.png?v=63725770322",
    title: "The Pragmatic Programmers",
  },
  {
    name: "Developer Tea",
    description:
      "A podcast for developers designed to fit inside your tea break",
    source: "https://spec.fm/podcasts/developer-tea/",
    audio:
      "https://cdn.simplecast.com/audio/c44db111-b60d-436e-ab63-38c7c3402406/episodes/f6c4dfce-4ef7-4746-8f8a-d286d6a0f7c9/audio/88ba9253-4f9b-4a83-9a97-6b9982c31193/default_tc.mp3?aid=embed",
    image: "https://spec.fm/static/img/shows/developertea.jpg",
    title: "Misusing Exceptions in Code and in Life\n",
  },
  {
    name: "Full Stack Radio",
    description:
      "A podcast for developers interested in building great software products.",
    source: "https://fullstackradio.com/",
    audio:
      "https://cdn.transistor.fm/file/transistor/m/shows/13470/2c4a84d725e57fc43b15b1d4b7302ac6.mp3",
    image: "https://fullstackradio.com/podcast-cover.jpg",
    title: "DHH – Building HEY with Hotwire",
  },
  {
    name: "Syntax",
    description: "A Tasty Treats Podcast for Web Developers.",
    source: "https://syntax.fm",
    audio: "https://traffic.libsyn.com/syntax/Syntax305.mp3",
    image: "https://syntax.fm/static/logo.png",
    title: "Writing Good Commit Messages",
  },
  {
    name: "Coder Radio",
    description:
      "A weekly talk show taking a pragmatic look at the art and business of Software Development and the world of technology.",
    source: "https://coder.show/",
    audio:
      "https://aphid.fireside.fm/d/1437767933/b44de5fa-47c1-4e94-bf9e-c72f8d1c8f5d/4d8d2cef-b64a-4464-a777-baa6313a37c9.mp3",
    image:
      "https://assets.fireside.fm/file/fireside-images/podcasts/images/b/b44de5fa-47c1-4e94-bf9e-c72f8d1c8f5d/header.jpg?v=5",
    title: "SaaS is a Blast",
  },
];

export const seedPodcasts = async (existing = []) => {

    const hash = existing.reduce((acc, p) => {
        acc[p.title] = p;
        return acc;
    }, {});

    const proc = podcasts.map(podcast => {
        return {
            ...podcast,
            audioURL: podcast.audio,
            imageURL: podcast.image,
            id: hash[podcast.title]? hash[podcast.title].id : null,
        }
    })

    console.log("seeding with ", proc);
    proc.forEach(content => Api.createContent(content));
}