package ru.gozerov.lyceum8_project.data.cache.room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.gozerov.lyceum8_project.data.cache.entity.DBNews
import ru.gozerov.lyceum8_project.data.cache.entity.DataNews
import ru.gozerov.lyceum8_project.data.cache.entity.DataNewsId
import javax.inject.Inject

class RoomNewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
): RoomNewsRepository {
    override suspend fun getRecentNews(): Flow<List<DataNews>> = withContext(Dispatchers.IO){
        return@withContext newsDao.getAllNews().toDataNewsListFlow()
    }

    override suspend fun getNewsById(dataNewsId: DataNewsId): DataNews {
        return newsDao.getNewsById(dataNewsId.id).toDataNews()
    }

    override suspend fun firstInitialization() = withContext(Dispatchers.IO) {
        if (newsDao.getNewsCount() == 0)
            newsDao.firstInitialization(createRecentNews().toDBNewsList())
    }

    private fun DBNews.toDataNews() = DataNews(id, imageUrl, title, description)
    private fun List<DBNews>.toDataNewsList() = map { it.toDataNews() }
    private fun Flow<List<DBNews>>.toDataNewsListFlow() = map { it.toDataNewsList() }
    private fun DataNews.toDBNews() = DBNews(id, imageUrl, title, description)
    private fun List<DataNews>.toDBNewsList() = map { it.toDBNews() }

    private fun createRecentNews() : List<DataNews> {
        val recentNews: List<DataNews> = (1..5).map {
            DataNews(
                it.toLong(),
                imageMap[it]!!,
                titleMap[it]!!,
                descriptionMap[it]!!
            ) }
        return recentNews
    }

    // ссылочка на каталог на imgur
    // https://imgur.com/a/WbKd6It

    private val imageMap = mapOf (
        1 to "http://lyceum8.perm.ru/images/9/pf3MZnq8Oos_1.jpg",
        2 to "http://lyceum8.perm.ru/images/9/rod.jpg",
        3 to "http://lyceum8.perm.ru/images/9/1_-_0001.jpg",
        4 to "http://lyceum8.perm.ru/images/9/str-upr-diktant-1024x764.jpg",
        5 to "http://lyceum8.perm.ru/images/6/perepis-1024x649.jpg")

    private val titleMap = mapOf (
        1 to "Бесплатные интенсивы для олимпиадников онлайн",
        2 to "Будьте ближе со своими детьми",
        3 to "Участие в дистанционном конкурсе «Премия фестиваля «Золотое перо – 2021»» в номинации «Фоторепортаж «Школа. Событие в кадре».",
        4 to "Об участии в VI Международной просветительской акции «Большой этнографический диктант»",
        5 to "Перепись населения 2021!"
    )

    private val descriptionMap = mapOf (
        1 to "В ноябре пермские старшеклассники могут посетить открытые вебинары по подготовке к Всероссийской олимпиаде школьников. Они пройдут бесплатно и онлайн.\n" +
                "\n" +
                "Для учеников 9-10 классов проведут вебинары по шести предметам: русский язык, литература, математика, обществознание, физика, биология.\n" +
                "Вести онлайн-интенсивы будут кандидаты наук, члены жюри муниципального и регионального этапа олимпиады.\n" +
                "\n" +
                "Для участия в вебинарах-интенсивах нужно заполнить форму: https://clck.ru/YVKPh.",
        2 to "В ПГГПУ открылся «Родительский университет»!\n" +
                "Обратиться за помощью может любой желающий родитель и это абсолютно бесплатно!\n" +
                "\n" +
                "Мы поможем решить такие проблемы, как:\n" +
                "\n" +
                "потребности и запросы родителей детей подросткового, школьного и дошкольного возраста;\n" +
                "воспитание, развитие, обучение и социализация детей подросткового возраста;\n" +
                "помощь родителям, воспитывающим детей с ограниченными возможностями;\n" +
                "помощь родителям с приемными детьми;\n" +
                "помощь семьям, оказавшимся в конфликтной ситуации и другие проблемы.\n" +
                "Кроме того, в «Родительском университете» также проходят очные консультации специалистов, обеспечивается ведение родительских лекториев и поддержка родителей в отдаленных районах Пермского края.\n" +
                "\n" +
                "Для того, чтобы получить бесплатную помощь, звоните по телефону\n" +
                "\n" +
                "215-20-11 или оставляйте заявку на сайте https://roditel.pspu.ru.\n" +
                "\n" +
                "Станьте ближе к своим детям!",
        3 to "Вот и пролетела  1 четверть! Впереди длинные каникулы. Есть время всё обдумать и проанализировать, а ещё … попробовать свои силы в очередном конкурсе! \n" +
                "Пожелаем Чаловой Валерии, ученице 9 «а» класса Лицея № 8, успехов в дистанционном конкурсе «Премия фестиваля «Золотое перо – 2021»» в номинации «Фоторепортаж «Школа. Событие в кадре».",
        4 to "Приглашаем педагогов, обучающихся, родителей принять участие\n" +
                "в Международной просветительской акции «Большой этнографический диктант» (далее – Акция, Диктант). \n" +
                "\n" +
                "Акция проводится в единый период – с 3 по 7 ноября 2021 года\n" +
                "в онлайн-формате на сайте www.miretno.ru и традиционно приурочена ко Дню народного Единства.\n" +
                "\n" +
                "Диктант позволит не только оценить уровень этнографической грамотности населения, их знания о народах, проживающих в России, но и привлечет внимание широкой общественности к вопросам межнационального мира\n" +
                "и согласия.",
        5 to "В соответствии с постановлением Правительства Российской Федерации от 7 декабря 2019 г. No 1608 «Об организации Всероссийской переписи населения 2020 года» в период с 15 октября по 14 ноября 2021 года на территории Российской Федерации пройдет Всероссийская перепись населения (далее – перепись):\n" +
                "\n" +
                "- с 15 октября по 14 ноября 2021 года на большей части территории России (за исключением отдаленных и труднодоступных территорий);\n" +
                "- с 15 октября до 08 ноября 2021 года -  на портале Госуслуги.\n" +
                "Кто может участвовать в переписи:\n" +
                "\n" +
                "- граждане России;\n" +
                "- иностранные граждане и лица без гражданства, постоянно проживающие на территории России.\n" +
                "Как участвовать в электронной переписи? Инструкция\n" +
                "\n" +
                "Горячая линия Всероссийской переписи населения -  8-800-707-20-20 \n" +
                "Задать вопрос о переписи можно с 9.00 до 21.00 по московскому времени.\n" +
                "Если позвонить позже или раньше, звонок примет автоответчик, а оператор перезвонит позднее и ответит на вопрос.\n" +
                "Линия открыта до 14 ноября.\n" +
                "\n" +
                "Более подробно о переписи населения -  https://www.strana2020.ru/ и https://disk.yandex.ru/d/vUDvMHhZmZ8eRA")

}