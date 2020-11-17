package com.example.liverpooldirectory

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.liverpooldirectory.players.Player
import kotlinx.android.synthetic.main.activity_players.*

class PlayersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        supportActionBar?.hide()

        val text = "Tap on players name, to read his bio."
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()

        textAlisson.setOnClickListener {
            val player = Player(
                number = 1,
                firstName = "Alisson",
                lastName = "Becker",
                age = 24,
                biography = "After two years of competition between Loris Karius and Simon Mignolet, and two years of consistent errors cumulating in the Champions League Final, Liverpool were in search of a top class goalkeeper ahead of the 2018-19 season. The rumoured two big targets were Jan Oblak of Atletico Madrid, and Alisson from Roma.\n\nOn 17 July 2018, after months of rumoured interest, it was reported that Liverpool had finally made a bid of £62,000,000 to bring Alisson to Anfield, with Roma looking for a little more for a deal to be agreed. A day later, reports stated that a deal had finally been agreed, for a fee of £66,800,000, a record fee for a goalkeeper, with the Brazilian set to fly in for a medical after agreeing personal terms.\n\nOn 19 July 2018, Alisson was spotted leaving John Lennon Airport after reports that he had passed his medical. Later that evening, it was confirmed that Alisson had signed for Liverpool on a six year deal, becoming the club’s second most expensive transfer, just after Virgil van Dijk.\n\nIt was confirmed that Alisson was to wear the number 13 shirt for the 2018-19 campaign, with Loris Karius continuing to wear the number 1.\n\nOn 12 August 2018, Alisson made his competitive debut, starting and keeping a clean sheet in a 4-0 win over West Ham. He kept up this run in his next two games, making vital saves in wins over Crystal Palace and Brighton.\n\nIn his fourth appearance, Alisson made a massive error leading to a goal, attempting to 'Cruyff turn' a Leicester attacker in his penalty area, but losing the ball and allowing Ghezzal to pull a goal back for Leicester. Fortunately for the Brazilian, the Reds held out to win 2-1 away from home.",
                photoRes = R.drawable.alisson,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textFabinho.setOnClickListener {
            val player = Player(
                3,
                "Tavares",
                "Fabinho",
                27,
                "With the midfield being widely regarded as the weakest part of the team, as well as the impending departure of Emre Can to Juventus, it was clear that Jurgen Klopp was looking to strengthen in this area. The signing of Naby Keita was already announced, but it was reported that he was also looking to strengthen the defensive side, with Jordan Henderson the only first-team player in this position.\nOn 28 May 2018, reports in France began to come through linking Fabinho with a move to Anfield, reports that were confirmed by club journalists hours later. In the evening, the club confirmed that a deal had been agreed to sign Fabinho from Monaco, and that the transfer would be formally completed on 1 July for an initial reported fee of £39,300,000.\n On 7 July 2018, Fabinho made his non-competitive debut, starting in a 7-0 friendly win over Chester. The Brazilian wore the number 3 shirt, but the club stated this would not be his official shirt number. Despite this, it was later confirmed that he would wear the number 3 shirt for the 2018-19 season.\n\nAfter failing to appear in the squad for the first few weeks of the season, on 18 September, Fabinho made his Liverpool debut as a late substitute in a 3-2 win over Paris Saint Germain. Just over a week later, the Brazilian made his first start, playing almost the full 90 minutes in a 2-1 cup defeat to Chelsea.\n\nAfter a few more promising substitute appearances, Fabinho returned to the starting lineup on 24 October against Red Star Belgrade, putting in a man-of-the-match performance in a 4-0 win. Three days later, he made his first league start, playing 90 minutes in a 4-1 win over Cardiff.\n\nOn 16 December 2018, Fabinho started in a 3-1 win over Manchester United, assisting Sadio Mane’s opener with a sublime dinked ball over the top of the United defence. Five days later, the Brazilian once again created the Reds’ opening goal, this time with a grounded cross for Mohamed Salah to fire Liverpool ahead in a 2-0 win over Wolves. On 26 December, Fabinho came off the bench to head in his first goal for the club in a 4-0 victory over Newcastle at Anfield.\n\nBy the end of the season, Fabinho had arguably become Liverpool's most important midfielder, seen as a starter in almost every game, with pundits giving high praise for how he had adapted to playing his holding midfield role. On 1 June 2019, Fabinho started in the Champions League final, and played the full 90 minutes as the Reds beat Tottenham 2-0 in Madrid.\n\nFabinho kicked off the 2019-20 season in the Reds' first-choice XI, starting in the Community Shield defeat to Manchester City. On 10 November 2019, Fabinho fired in his first goal of the campaign, opening the scoring in a vital league victory over Manchester City, with a powerful strike from range, picking up the man-of-the-match award for his dominating performance.",
                photoRes = R.drawable.fabinhophoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textVirgil.setOnClickListener {
            val player = Player(
                4,
                "Вирджил",
                "Ван Дайк",
                29,
                "After another relatively poor defensive season in 2016-17, Jurgen Klopp decided his centre-back pairing was in need of reinforcement. Very early in the summer reports linking Liverpool with the Southampton captain, which would make him the sixth player to sign from the Saints since 2014. Around the same time reports began to come through that Southampton were unhappy with Liverpool’s “illegal” approaches to van Dijk without any Saints approval.\n\nOn 7 June 2017, Liverpool released an official statement apologising for media reports considering the club’s apparent tapping up of the Dutchman, stating that they had ended any interest in van Dijk. Despite this reports continued linking the player with signing throughout the summer, even ahead of deadline day club affiliated journalists maintained that the club were still set on signing the Dutchman, but a deal wasn’t agreed. \n\nAfter a day of build-up, it was officially announced that Liverpool had agreed a deal to sign van Dijk in the January window, with a world record fee for a defender of £75,000,000, which made him the club’s record transfer. Van Dijk officially signed on 1 January 2018, and was set to wear the no. 4 shirt at the club. \n\nDespite only having appeared in a few training sessions, van Dijk started in his first game against Everton in the FA Cup on 5 January. With the scores level, the Dutchman jumped high to head in Alex Oxlade-Chamberlain’s corner, scoring the winning goal in the 84th minute of the Merseyside Derby, sending Liverpool into the next round of the FA Cup.\n\nVan Dijk quickly became a key part of Liverpool's starting XI, and was vital in the Reds' run to the Champions League final, where he played all 90 minutes in a 3-1 defeat to Real Madrid.\n\nThe 2018-19 season began with van Dijk as the club's first-choice centre-back, starting alongside Joe Gomez. The two formed a formidable partnership in the opening months of the season, conceding just five goals in the first fourteen League games of the season. The pair were helped either side by Andy Robertson and Trent Alexander-Arnold, as well as the signing of Alisson in goal. Van Dijk was also named the club’s third-captain, behind Henderson and Milner. He started as captain for the first time on 24 October 2018, in a 4-0 win over Red Star Belgrade.\n\nOn 21 December 2018, van Dijk scored his first goal of the season, volleying Liverpool's second in an impressive 2-0 win away to Wolves. His offensive form began to pick up after this, with the Dutchman going in to grab a brace of headers in a 5-0 victory over Watford at Anfield.\n\n On 13 March 2019, van Dijk started in a crucial Champions League tie away to Bayern Munich. Early in the game, he fired a superb long ball forward to Sadio Mane, who took it down perfectly before rounding the goalkeeper and putting Liverpool a goal up. Later on with the scores level, the defender rose high to head home a James Milner corner to give the Reds a 2-1 lead, and effectively sending them into the quarter-finals. \n\n Van Dijk went on to feature in all 38 Premier League games, and was a massive part of Liverpool's record breaking push for the title, unfortunately only reaching 2nd place despite a 97 point haul. On 28 April 2019, he was named the PFA Player of Year for his amazing defensive efforts throughout the campaign, as Liverpool went on to concede the least goals and keep the most clean sheets. \n\nOn 1 June 2019, van Dijk started in a second consecutive Champions League final. This time round, Liverpool were able to prevail, beating Tottenham Hotspur 2-0 in Madrid, with the Dutchman being named man-of-the-match.\n\nVan Dijk started in the opening game of the new Premier League season, a 4-1 win over Norwich, and headed in the Reds’ third of the game from a Mohamed Salah corner. On 30 November, the Dutchman scored both of Liverpool's goals in a 2-1 victory over Brighton, with two great headers from Trent Alexander-Arnold deliveries. \n\nOn 29 December 2019, van Dijk made his 100th appearance for the club, in a 1-0 win over Wolves. Three weeks later, he headed in the opening goal in a vital 2-0 win over Manchester United.",
                photoRes = R.drawable.vandijk_biophoto,
                sound = R.raw.virgil_chant
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textSalah.setOnClickListener {
            val player = Player(
                11,
                "Мохаммед",
                "Салах",
                28,
                "With Sadio Mane having been missing for large periods of the 2016-17 season with no real backup, Jurgen Klopp looked to sign a proven quality winger, to add more depth in the position. Salah had been close to signing for Liverpool back in 2014, and had shown quality performances for Roma in the past few seasons.\n\nIn May 2017 reports began to emerge that Liverpool were again interested in the Egyptian, but Roma were insisting on a fee of around £40,000,000, which the Reds saw as too much. Weeks later, it seemed that the price had been haggled down, and on 21 June 2017 reliable journalists stated that a much lower fee had been accepted by Roma, and that Salah was to complete a medical the next day.\n\nOn 22 June 2017, it was confirmed that Salah had signed for Liverpool on a five year deal for a reported fee of £36,900,000, making him the club's record signing, surpassing Andy Carroll by almost £2,000,000. It was confirmed that he was to wear the number 11 shirt, given to him by Roberto Firmino.\n\nSalah made his unofficial debut on 14 July 2017, playing 45 minutes and scoring his first goal in a 1-1 draw against Wigan.\n\nAfter an impressive pre-season, netting four goals, Salah was named in the starting lineup for the opening game of the season. With the Reds 2-1 down, Salah won a penalty which was converted by Roberto Firmino, before Firmino played a lovely dinked ball allowing Salah to give Liverpool the lead on his debut. This looked to be the winning goal before poor defending caused the Reds to concede a last gasp equaliser. On 23 August, Salah netted Liverpool's second in an eventual 4-2 over Hoffenheim in the Champions League playoff, tapping it in after Georginio Wijnaldum's effort struck the post. \\n\\nOn 27 August 2017, Salah was named man-of-the-match after a 4-0 win over Arsenal at Anfield. Salah scored Liverpool's third of the game, running the length of the pitch after an Arsenal scorned before coolly finishing past Petr Cech. Salah then went on to assist Daniel Sturridge's goal with an inch perfect cross. Salah was later named Liverpool's Player of the Month in his first month at the club. \n\nSalah's form continued to be very impressive, with the Egyptian netting a further three goals in September. On 17 October 2017, Salah scored a brace, and assisted a Roberto Firmino goal in a 7-0 win over Maribor. In his next four games, Salah netted four times, with the last two being two very impressive finishes in a 4-1 win over West Ham. \\n\\nOn 18 November 2017, Salah scored twice in a 3-0 win over Southampton. The first was a lovely curling effort into the corner from the edge of the box, whilst the other was a cute close range finish after a fantastic ball from Philippe Coutinho. Salah’s second was his 9th goal in 12 Premier League appearances, more than any other Liverpool player in history, eclipsing Robbie Fowler’s record. A week later, he became the first player to score 10 Premier League goals this season, opening the scoring in a 1-1 draw against his former club, Chelsea. On 29 November 2017, Salah came on with 20 minutes to play, and scored twice in a 3-0 win over Stoke. This took him to eight goals in November alone. His incredible performances led to him being named PFA Player of the Month.\nDecember carried on with Salah’s fantastic form. On 6 December, Salah rounded off a 7-0 Champions League win over Spartak Moscow with a good finish from inside the area. Four days later, Salah opened the scoring in the Merseyside Derby, with an incredible curling finish from the right side of the penalty area. \\n\\nOn 17 December 2017, Salah scored his 20th goal for Liverpool, after just 26 games. This meant he matched Daniel Sturridge’s record, with only George Allan in 1895 beating the two. A followed this up with another in his next game, netting Liverpool’s second in an eventual 3-3 draw with Arsenal. On 30 December, Salah scored twice as Liverpool came from a goal down to beat Leicester City at Anfield. \\n\\nAfter two weeks out through injury, Salah returned to first team action in a 4-3 win over Manchester City, who had previously been undefeated in the Premier League. Salah scored the winning goal, curling into an empty net from 30 yards after the City goalkeeper’s clearance fell right to him. Salah had also previously assisted Sadio Mane’s effort. Salah continued his run with four goals in his next four appearances, including two world class efforts in a 2-2 draw with Tottenham, which were his 20th and 21st league goals of the season. He managed this feat in just 25 appearances, faster than any Liverpool player in over 100 years. He followed his up with Liverpool’s second in a 2-0 win away to Southampton, finishing a 1v1 opportunity following a lovely backheel from Roberto Firmino. \\n\\nOn 14 February 2018, Salah scored his 30th goal of the season, latching on to James Milner’s rebounded effort, before juggling the ball over the goalkeeper and finishing coolly, to grab Liverpool’s second in an eventual 5-0 win away to Porto. This made him the second fastest to ever reach this landmark. He added one further to do this in his next game, striking the second in a 4-1 win over West Ham. Salah’s form led to him being named Premier League Player of the Month a second time. On 3 March 2018, Salah netted for the seventh game in a row, opening the scoring in an important 2-0 win over Newcastle.\n\nOn 17 March 2018, Salah scored his first hattrick for the club, netting four in a 5-0 win over Watford. The first was a sublime solo effort, leaving a defender grounded after cutting inside and sliding it past the keeper with his right foot. The second was much simpler, getting on the end of a sublime Andy Robertson cross to put it past the goalkeeper. The third was perhaps the pick of the bunch, dancing and dallying with three defenders before slotting it into the corner. The final finish was a powerful effort after a Danny Ings strike was stopped by the keeper. Salah had also previously assisted Roberto Firmino’s effort.\n\nAfter the international break Salah continued his world-class goalscoring form, finishing a late 1v1 with great composure to earn Liverpool a 2-1 win away to Crystal Palace. This was his 37th strike of the season, the most any Liverpool player had managed in a single season since Ian Rush in 1986-87. His six goals throughout Marchled to him being named Premier League Player of the Month for the third time, the only player to achieve that in a single season.\n\nFour days later, Salah started in the first leg of the Champions League quarter-final, against Premier League rivals Manchester City. Eleven minutes in, a move started off by the Egyptian ended up at the feet of Roberto Firmino, who slid it across to allow Salah to open the scoring with his 38th goal of the season. Twenty minutes later, Salah’s precise cross landed on the head of Sadio Mane, who headed home Liverpool’s third of the night. Salah was substituted early in the second half as the Reds continued to a 3-0 win. Six days later, Salah netted the equaliser in an eventual 2-1 win over City in the second leg, with a fine chip to send Liverpool into the semi-finals of the Champions League for the first time in ten years.\n\nSalah scored his 30th Premier League goal of the season, and his 40th in all competitions, after a lobbed header from a superb Trent Alexander-Arnold ball in a 3-0 win over Bournemouth. A week later, Salah netted the second in a 2-2 draw away to West Brom, with a lovely chip to reach his 31st League goal of the season, matching the record for a 38 game season. The next day, Salah was named PFA Player of the Season, becoming the seventh Liverpool player to receive the award.\n\nOn 24 April 2018, Salah started against his former club, Roma in the first leg of the Champions League semi-final. Salah netted two exquisite goals, the first being a powerful curving strike into the top left corner, and the second being a dinked finish over Allison from the edge of the area. This took him to 43 goals for the campaign, with only Ian Rush (47) managing more goals in a single season. Salah also assisted goals for Sadio Mane and Roberto Firmino in an eventual 5-2 win. In the final league game of the season, Salah opened the scoring to reach his 32nd Premier League goal of the season, more than any other player in a 38 game season, winning him the golden boot.\n\nOn 26 May 2018, Salah started in the Champions League final against Real Madrid, but was substituted after 30 minutes, after a Sergio Ramos challenge tore ligaments in his shoulder. Liverpool lost the final 3-1.\n\nAfter an incredible debut season with the club, Salah was awarded a new five-year contract, making him the highest earner at the club.\n\nSalah returned to Liverpool for pre-season, and opened his account just seconds after returning to action in a 2-1 friendly win over Manchester City. \n\nOn 12 August 2018, Salah scored his first goal of the 2018-19 season, opening the scoring in a 4-0 win over West Ham. A week later, Salah won a penalty and assisted Sadio Mane in a 2-0 win over Crystal Palace. On 25 August, Salah scored the only goal in a 1-0 win over Brighton.\n\nAfter a 3-0 win over Southampton in September, Salah went on a run of four games without a goal, leading some to criticise him for a poor goal output. On 20 October, Salah ended this run, scoring the only goal in a 1-0 win over Huddersfield with a sublime finish on his weaker foot. Four days later, Salah scored twice in a 4-0 win over Red Star Belgrade. The second was Salah’s 50th for the club in just 65 appearances. This made him the fastest player to reach this record. In the next game, Salah continued his impressive vein of form, opening the scoring and picking up two assists in a 4-1 win over Cardiff. \n\nOn 8 December 2018, Salah scored his second Liverpool hat-trick, netting three of the Reds’ goals in a 4-0 win away to Bournemouth. The first was a first-time effort after Roberto Firmino’s strike was parried by Asmir Begovic, while the second was a fine effort after being put through by Firmino, finding the bottom corner despite being fouled while dribbling. The third was a sublime individual effort, battling off the centre-back before rounding the keeper twice and slotting calmly into the open net\n\nThree days later, Salah scored the winning goal in a crucial Champions League tie against Napoli, out-muscling one defender before beating another and sliding it through the legs of the goalkeeper, to send Liverpool into the knockout stages. On 22 December, Salah opened the scoring in a big 2-0 win away to Wolves, meeting a Fabinho cross with a great finish. Four days later, the Egyptian grabbed the second in a 4-0 win over Newcastle, confidently placing his penalty into the bottom corner. In the next game, Salah smashes in another from the penalty spot, grabbing the fourth in a 5-1 victory over Arsenal. \n\nOn 12 January 2019, Salah scored the winner from the penalty spot away to Brighton. A week later, the Egyptian netted a brace in an eventual 4-3 win over Crystal Palace at Anfield, making him the League's top scorer at the time, with sixteen. On 9 February, Salah rounded off a 3-0 win over Bournemouth, finishing well after a lovely backheel from Roberto Firmino, and grabbing his 20th goal of the season. \n\nAfter this however, Salah went on a drought of eight matches without a single goal, the longest drought of his Liverpool career. This came to an end of 5 April, as he picked the ball up in his own area to run straight at the lone defender before firing in from the edge of the box. This was the decisive goal in an eventual 3-1 win away to Southampton. The goal also made him the quickest Liverpool player to reach 50 League goals (69), beating Fernando Torres' record of 72. Eight days later, Salah scored a contender for goal of the season in a 2-0 win over his former club, Chelsea. He picked the ball up on the right wing, before cutting inside and arrowing it into the top corner from thirty yards. \n\nOn 27 April 2019, Salah made his 100th appearance in a Liverpool shirt, starting in a 5-0 win over Huddersfield. The Egyptian assisted Naby Keita's opening goal after just 14 seconds, before going on to grab two goals for himself. These took him to a tally of 69, the most of any Liverpool player in their first century of appearances. A week later, Salah scored his 22nd League goal of the season, which proved to be enough to win the Golden Boot for a second consecutive season. \n\nOn 1 June 2019, Salah started in a second consecutive Champions League Final. Just over 20 seconds in, Liverpool won a penalty, allowing Salah to step up and smash the Reds into an early lead. Liverpool went on to win a sixth European Cup, Salah’s first honour with the club. \n\nSalah made 53 appearances in the 2018-19 season, more than any other Liverpool player, and was the club’s top scorer (with 27) for the second season in a row. \n\nOn 9 August 2019, Salah started on the opening day of the season, a 4-1 win over Norwich. He scored one and picked up an assist, making it three years in a row he had netted on the opening day. Two weeks later, Salah grabbed a brace in a 3-1 win over Arsenal. The first a perfectly placed penalty, while the second was a magnificent individual effort, taking it from near the half-way line into the area, before slotting it into the corner.",
                photoRes = R.drawable.mohamedsalah,
                sound = R.raw.salahchant
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textFirmino.setOnClickListener {
            val player = Player(
                9,
                "Roberto",
                "Firmino",
                29,
                "",
                photoRes = R.drawable.firminophoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textMane.setOnClickListener {
            val player = Player(
                10,
                "Sadio",
                "Mane",
                28,
                "",
                photoRes = R.drawable.manephoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textWijnaldum.setOnClickListener {
            val player = Player(
                5,
                "Giorginho",
                "Wijnaldum",
                29,
                "",
                photoRes = R.drawable.wijnaldumphoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textHenderson.setOnClickListener {
            val player = Player(
                14,
                "Jordan",
                "Henderson",
                30,
                "",
                photoRes = R.drawable.hendersonphoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textRobertson.setOnClickListener {
            val player = Player(
                26,
                "Andrew",
                "Robertson",
                26,
                "",
                photoRes = R.drawable.robertsonphoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textGomez.setOnClickListener {
            val player = Player(
                12,
                "Joseph",
                "Gomez",
                23,
                "",
                photoRes = R.drawable.gomezphoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }

        textTAA.setOnClickListener {
             val player = Player(
                66,
                "Trent",
                "Alexander-Arnold",
                22,
                "",
                photoRes = R.drawable.taaphoto,
                sound = 0
            )
            val dialog = PlayerDialogFragment.newInstance(player)
            dialog.show(supportFragmentManager, null)
        }
    }
}

