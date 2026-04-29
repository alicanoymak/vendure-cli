package cli;

import java.util.List;

public class TableProductFormatter implements ProductFormatter {

    @Override
    public String format(List<Product> products) {
        StringBuilder builder = new StringBuilder();
        builder.append("Name      Price\n");

        for (Product p : products) {
            builder.append(p.getName())
                    .append("   ")
                    .append(p.getPrice())
                    .append("\n");
        }

        return builder.toString();
    }
}