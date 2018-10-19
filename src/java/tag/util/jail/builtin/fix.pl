foreach (<jail_*.java>)
{
   my $newname = $_;
   $newname =~ s/jail/Jail/;
   rename $_, $newname;
}